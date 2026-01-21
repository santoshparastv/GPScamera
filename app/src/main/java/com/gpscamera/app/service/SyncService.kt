package com.gpscamera.app.service

import android.content.Context
import com.gpscamera.app.data.database.AppDatabase
import com.gpscamera.app.data.model.CaptureData
import com.gpscamera.app.repository.FirebaseRepository
import com.gpscamera.app.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncService(private val context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val firebaseRepository = FirebaseRepository()
    private val networkHelper = NetworkHelper(context)
    
    suspend fun syncPendingData(): Result<Int> = withContext(Dispatchers.IO) {
        if (!networkHelper.isNetworkAvailable()) {
            return@withContext Result.failure(Exception("No network connection"))
        }
        
        try {
            val unsyncedCaptures = database.captureDao().getUnsyncedCaptures()
            var syncedCount = 0
            
            unsyncedCaptures.forEach { capture ->
                val result = firebaseRepository.saveCapture(capture)
                if (result.isSuccess) {
                    val updatedCapture = capture.copy(isSynced = true)
                    database.captureDao().updateCapture(updatedCapture)
                    syncedCount++
                }
            }
            
            Result.success(syncedCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadPendingImages(): Result<Int> = withContext(Dispatchers.IO) {
        if (!networkHelper.isNetworkAvailable()) {
            return@withContext Result.failure(Exception("No network connection"))
        }
        
        try {
            val unuploadedCaptures = database.captureDao().getUnuploadedCaptures()
            var uploadedCount = 0
            
            unuploadedCaptures.forEach { capture ->
                if (capture.imagePath.isNotEmpty()) {
                    val uri = android.net.Uri.parse(capture.imagePath)
                    val uploadResult = firebaseRepository.uploadImage(uri, capture.userId)
                    
                    if (uploadResult.isSuccess) {
                        val imageUrl = uploadResult.getOrNull() ?: ""
                        val updatedCapture = capture.copy(
                            imageUrl = imageUrl,
                            isUploaded = true
                        )
                        database.captureDao().updateCapture(updatedCapture)
                        uploadedCount++
                    }
                }
            }
            
            Result.success(uploadedCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
