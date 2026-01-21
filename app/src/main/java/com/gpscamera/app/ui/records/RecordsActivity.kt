package com.gpscamera.app.ui.records

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gpscamera.app.databinding.ActivityRecordsBinding
import com.gpscamera.app.data.database.AppDatabase
import com.gpscamera.app.ui.records.adapter.RecordsAdapter
import com.gpscamera.app.util.SharedPreferencesHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecordsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordsBinding
    private lateinit var database: AppDatabase
    private lateinit var prefsHelper: SharedPreferencesHelper
    private lateinit var adapter: RecordsAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        database = AppDatabase.getDatabase(this)
        prefsHelper = SharedPreferencesHelper(this)
        
        setupToolbar()
        setupRecyclerView()
        loadRecords()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        adapter = RecordsAdapter()
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
        binding.rvRecords.adapter = adapter
    }
    
    private fun loadRecords() {
        val userId = prefsHelper.getUserId() ?: return
        
        lifecycleScope.launch {
            database.captureDao().getCapturesByUser(userId).collectLatest { captures ->
                if (captures.isEmpty()) {
                    binding.rvRecords.visibility = android.view.View.GONE
                    binding.tvNoRecords.visibility = android.view.View.VISIBLE
                } else {
                    binding.rvRecords.visibility = android.view.View.VISIBLE
                    binding.tvNoRecords.visibility = android.view.View.GONE
                    adapter.submitList(captures)
                }
            }
        }
    }
}
