package com.gpscamera.app.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.gpscamera.app.databinding.ActivityCameraBinding
import com.gpscamera.app.service.LocationService
import com.gpscamera.app.ui.input.InputActivity
import com.gpscamera.app.util.PermissionHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var locationService: LocationService
    private var capturedImageUri: android.net.Uri? = null
    private var capturedLocation: android.location.Location? = null
    
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val LOCATION_PERMISSION_CODE = 101
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        locationService = LocationService(this)
        
        if (checkPermissions()) {
            startCamera()
        } else {
            requestPermissions()
        }
        
        binding.fabCapture.setOnClickListener {
            capturePhoto()
        }
    }
    
    private fun checkPermissions(): Boolean {
        return PermissionHelper.hasCameraPermission(this) &&
               PermissionHelper.hasLocationPermission(this)
    }
    
    private fun requestPermissions() {
        val permissions = mutableListOf<String>()
        
        if (!PermissionHelper.hasCameraPermission(this)) {
            permissions.add(Manifest.permission.CAMERA)
        }
        
        if (!PermissionHelper.hasLocationPermission(this)) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                CAMERA_PERMISSION_CODE
            )
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == CAMERA_PERMISSION_CODE || requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Camera and location permissions are required",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }
            
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Toast.makeText(this, "Camera error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }
    
    private fun capturePhoto() {
        val imageCapture = imageCapture ?: return
        
        binding.fabCapture.isEnabled = false
        binding.tvLocationStatus.text = "Fetching location..."
        
        // Fetch location first
        lifecycleScope.launch {
            try {
                val locationResult = locationService.getCurrentLocation().first()
                capturedLocation = locationResult.lastLocation
                
                val accuracy = capturedLocation?.accuracy ?: 0f
                binding.tvLocationStatus.text = "Location fetched"
                binding.tvGpsAccuracy.text = "GPS Accuracy: ${String.format("%.2f", accuracy)} meters"
                
                // Capture image
                val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                    .format(System.currentTimeMillis())
                val contentValues = android.content.ContentValues().apply {
                    put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                }
                
                val outputOptions = ImageCapture.OutputFileOptions
                    .Builder(
                        contentResolver,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    .build()
                
                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(this@CameraActivity),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            capturedImageUri = output.savedUri
                            navigateToInput()
                        }
                        
                        override fun onError(exception: androidx.camera.core.ImageCaptureException) {
                            Toast.makeText(
                                this@CameraActivity,
                                "Photo capture failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.fabCapture.isEnabled = true
                        }
                    }
                )
            } catch (e: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Location error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                binding.fabCapture.isEnabled = true
            }
        }
    }
    
    private fun navigateToInput() {
        val intent = Intent(this, InputActivity::class.java).apply {
            capturedImageUri?.let { putExtra("image_uri", it.toString()) }
            capturedLocation?.let {
                putExtra("latitude", it.latitude)
                putExtra("longitude", it.longitude)
                putExtra("accuracy", it.accuracy)
            }
        }
        startActivity(intent)
        finish()
    }
}
