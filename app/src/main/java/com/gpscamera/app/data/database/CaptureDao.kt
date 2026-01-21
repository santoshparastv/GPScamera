package com.gpscamera.app.data.database

import androidx.room.*
import com.gpscamera.app.data.model.CaptureData
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {
    @Query("SELECT * FROM captures ORDER BY createdAt DESC")
    fun getAllCaptures(): Flow<List<CaptureData>>
    
    @Query("SELECT * FROM captures WHERE userId = :userId ORDER BY createdAt DESC")
    fun getCapturesByUser(userId: String): Flow<List<CaptureData>>
    
    @Query("SELECT * FROM captures WHERE isSynced = 0")
    suspend fun getUnsyncedCaptures(): List<CaptureData>
    
    @Query("SELECT * FROM captures WHERE isUploaded = 0")
    suspend fun getUnuploadedCaptures(): List<CaptureData>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapture(capture: CaptureData)
    
    @Update
    suspend fun updateCapture(capture: CaptureData)
    
    @Delete
    suspend fun deleteCapture(capture: CaptureData)
    
    @Query("SELECT * FROM captures WHERE captureId = :captureId LIMIT 1")
    suspend fun getCaptureById(captureId: String): CaptureData?
}
