package com.gpscamera.app.ui.input

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gpscamera.app.databinding.ActivityInputBinding
import com.gpscamera.app.ui.preview.PreviewActivity
import kotlinx.coroutines.launch

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private var imageUri: Uri? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var accuracy: Float = 0f
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get data from previous activity
        intent.getStringExtra("image_uri")?.let {
            imageUri = Uri.parse(it)
        }
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
        accuracy = intent.getFloatExtra("accuracy", 0f)
        
        binding.btnNext.setOnClickListener {
            validateAndProceed()
        }
    }
    
    private fun validateAndProceed() {
        val inputId = binding.etInputId.text.toString().trim()
        val remarks = binding.etRemarks.text.toString().trim()
        
        // Validate input ID
        if (inputId.isEmpty()) {
            binding.etInputId.error = "Input ID is required"
            return
        }
        
        // Check size (1KB = 1024 bytes)
        val inputIdBytes = inputId.toByteArray(Charsets.UTF_8)
        if (inputIdBytes.size > 1024) {
            binding.etInputId.error = "Input ID exceeds 1KB limit"
            return
        }
        
        // Validate alphanumeric (optional - can be removed if special chars are allowed)
        if (!inputId.matches(Regex("^[a-zA-Z0-9]+$"))) {
            binding.etInputId.error = "Input ID should be alphanumeric"
            return
        }
        
        // Navigate to preview
        val intent = Intent(this, PreviewActivity::class.java).apply {
            imageUri?.let { putExtra("image_uri", it.toString()) }
            putExtra("latitude", latitude)
            putExtra("longitude", longitude)
            putExtra("accuracy", accuracy)
            putExtra("input_id", inputId)
            putExtra("remarks", remarks)
        }
        startActivity(intent)
        finish()
    }
}
