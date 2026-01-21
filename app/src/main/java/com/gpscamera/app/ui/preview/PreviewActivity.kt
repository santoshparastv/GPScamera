package com.gpscamera.app.ui.preview

import android.graphics.Bitmap
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gpscamera.app.databinding.ActivityPreviewBinding
import com.gpscamera.app.data.database.AppDatabase
import com.gpscamera.app.data.model.CaptureData
import com.gpscamera.app.repository.FirebaseRepository
// import com.gpscamera.app.service.GoogleSheetsService // Disabled - add dependencies to enable
import com.gpscamera.app.service.LocationService
import com.gpscamera.app.ui.camera.CameraActivity
import com.gpscamera.app.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private var imageUri: Uri? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var accuracy: Float = 0f
    private var inputId: String = ""
    private var remarks: String = ""
    private lateinit var prefsHelper: SharedPreferencesHelper
    private lateinit var database: AppDatabase
    private val firebaseRepository = FirebaseRepository()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefsHelper = SharedPreferencesHelper(this)
        database = AppDatabase.getDatabase(this)
        
        // Get data from previous activity
        intent.getStringExtra("image_uri")?.let {
            imageUri = Uri.parse(it)
        }
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
        accuracy = intent.getFloatExtra("accuracy", 0f)
        inputId = intent.getStringExtra("input_id") ?: ""
        remarks = intent.getStringExtra("remarks") ?: ""
        
        setupToolbar()
        loadPreviewImage()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadPreviewImage() {
        imageUri?.let { uri ->
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                binding.ivPreview.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Toast.makeText(this, "Error loading image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.btnRetake.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            finish()
        }
        
        binding.btnSave.setOnClickListener {
            saveCapture()
        }
    }
    
    private fun saveCapture() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.btnSave.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val userId = prefsHelper.getUserId() ?: return@launch
                val userName = prefsHelper.getUserName() ?: "Unknown"
                val deviceId = DeviceHelper.getDeviceId(this@PreviewActivity)
                
                // Get address
                val locationService = LocationService(this@PreviewActivity)
                val address = withContext(Dispatchers.IO) {
                    locationService.getAddress(latitude, longitude)
                }
                
                // Process image with watermark
                val watermarkedBitmap = processImageWithWatermark()
                
                // Save watermarked image
                val savedImagePath = saveWatermarkedImage(watermarkedBitmap)
                
                val now = Date()
                val captureData = CaptureData(
                    captureId = UUID.randomUUID().toString(),
                    userId = userId,
                    userName = userName,
                    imagePath = savedImagePath,
                    imageUrl = "",
                    latitude = latitude,
                    longitude = longitude,
                    address = address,
                    captureDate = DateFormatter.formatDate(now),
                    captureTime = DateFormatter.formatTime(now),
                    inputId = inputId,
                    remarks = remarks,
                    deviceId = deviceId,
                    gpsAccuracy = accuracy,
                    createdAt = now.time,
                    isSynced = false,
                    isUploaded = false
                )
                
                // Save to local database
                withContext(Dispatchers.IO) {
                    database.captureDao().insertCapture(captureData)
                }
                
                // Try to upload if network is available
                val networkHelper = NetworkHelper(this@PreviewActivity)
                if (networkHelper.isNetworkAvailable()) {
                    uploadAndSync(captureData, Uri.parse(savedImagePath))
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@PreviewActivity,
                            "Saved offline. Will sync when online.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PreviewActivity,
                        "Error saving: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnSave.isEnabled = true
                }
            }
        }
    }
    
    private suspend fun processImageWithWatermark(): Bitmap = withContext(Dispatchers.IO) {
        val originalBitmap = imageUri?.let { uri ->
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } ?: throw Exception("Image not found")
        
        val userName = prefsHelper.getUserName() ?: "Unknown"
        val now = Date()
        
        ImageWatermark.addWatermark(
            originalBitmap,
            userName,
            inputId,
            latitude,
            longitude,
            now
        )
    }
    
    private suspend fun saveWatermarkedImage(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val fileName = "capture_${System.currentTimeMillis()}.jpg"
        val file = File(getExternalFilesDir(null), fileName)
        
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }
        
        file.absolutePath
    }
    
    private fun uploadAndSync(captureData: CaptureData, imageUri: Uri) {
        lifecycleScope.launch {
            try {
                // Upload image
                val uploadResult = withContext(Dispatchers.IO) {
                    firebaseRepository.uploadImage(imageUri, captureData.userId)
                }
                
                if (uploadResult.isSuccess) {
                    val imageUrl = uploadResult.getOrNull() ?: ""
                    val updatedCapture = captureData.copy(
                        imageUrl = imageUrl,
                        isUploaded = true
                    )
                    
                    // Save to Firestore
                    val saveResult = withContext(Dispatchers.IO) {
                        firebaseRepository.saveCapture(updatedCapture)
                    }
                    
                    if (saveResult.isSuccess) {
                        // Update local database
                        withContext(Dispatchers.IO) {
                            database.captureDao().updateCapture(updatedCapture.copy(isSynced = true))
                        }
                        
                        // Export to Google Sheet (if configured)
                        // Note: Google Sheets export is disabled - add dependencies to enable
                        // val sheetId = prefsHelper.getGoogleSheetId()
                        // if (!sheetId.isNullOrEmpty()) {
                        //     try {
                        //         val sheetsService = GoogleSheetsService(this@PreviewActivity)
                        //         withContext(Dispatchers.IO) {
                        //             sheetsService.appendToSheet(sheetId, "Sheet1", updatedCapture)
                        //         }
                        //     } catch (e: Exception) {
                        //         // Google Sheets export failed, but continue
                        //     }
                        // }
                        
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@PreviewActivity,
                                "Upload successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    } else {
                        throw Exception("Failed to save to database")
                    }
                } else {
                    throw Exception("Failed to upload image")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PreviewActivity,
                        "Upload failed. Saved offline.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnSave.isEnabled = true
                }
            }
        }
    }
}
