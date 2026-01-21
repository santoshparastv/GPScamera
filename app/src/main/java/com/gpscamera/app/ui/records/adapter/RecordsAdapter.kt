package com.gpscamera.app.ui.records.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gpscamera.app.data.model.CaptureData
import com.gpscamera.app.databinding.ItemRecordBinding

class RecordsAdapter : ListAdapter<CaptureData, RecordsAdapter.RecordViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecordViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class RecordViewHolder(
        private val binding: ItemRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(capture: CaptureData) {
            binding.tvInputId.text = "ID: ${capture.inputId}"
            binding.tvDate.text = "Date: ${capture.captureDate}"
            binding.tvTime.text = "Time: ${capture.captureTime}"
            binding.tvLocation.text = "Location: ${String.format("%.6f, %.6f", capture.latitude, capture.longitude)}"
            binding.tvAddress.text = "Address: ${capture.address}"
        }
    }
    
    class DiffCallback : DiffUtil.ItemCallback<CaptureData>() {
        override fun areItemsTheSame(oldItem: CaptureData, newItem: CaptureData): Boolean {
            return oldItem.captureId == newItem.captureId
        }
        
        override fun areContentsTheSame(oldItem: CaptureData, newItem: CaptureData): Boolean {
            return oldItem == newItem
        }
    }
}
