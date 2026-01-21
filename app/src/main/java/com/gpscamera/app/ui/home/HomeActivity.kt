package com.gpscamera.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gpscamera.app.databinding.ActivityHomeBinding
import com.gpscamera.app.service.SyncService
import com.gpscamera.app.ui.camera.CameraActivity
import com.gpscamera.app.ui.login.LoginActivity
import com.gpscamera.app.ui.records.RecordsActivity
import com.gpscamera.app.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var prefsHelper: SharedPreferencesHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefsHelper = SharedPreferencesHelper(this)
        
        setupToolbar()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
    
    private fun setupClickListeners() {
        binding.btnCapturePhoto.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        
        binding.btnViewRecords.setOnClickListener {
            startActivity(Intent(this, RecordsActivity::class.java))
        }
        
        binding.btnSyncPending.setOnClickListener {
            syncPendingData()
        }
        
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }
    
    private fun syncPendingData() {
        lifecycleScope.launch {
            try {
                val syncService = SyncService(this@HomeActivity)
                val result = syncService.syncPendingData()
                
                if (result.isSuccess) {
                    val count = result.getOrNull() ?: 0
                    Toast.makeText(
                        this@HomeActivity,
                        "Synced $count records",
                        Toast.LENGTH_SHORT
                    ).show()
                    
                    // Also upload pending images
                    syncService.uploadPendingImages()
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "Sync failed: ${result.exceptionOrNull()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@HomeActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private fun logout() {
        prefsHelper.clearUserData()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
