package com.gpscamera.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "captures")
data class CaptureData(
    @PrimaryKey
    val captureId: String = "",
    val userId: String = "",
    val userName: String = "",
    val imagePath: String = "", // Local path before upload
    val imageUrl: String = "", // Cloud URL after upload
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val captureDate: String = "", // YYYY-MM-DD
    val captureTime: String = "", // HH:MM:SS
    val inputId: String = "",
    val remarks: String = "",
    val deviceId: String = "",
    val gpsAccuracy: Float = 0f,
    val createdAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false,
    val isUploaded: Boolean = false
)
