package com.gpscamera.app.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gpscamera.app.data.model.CaptureData
import com.gpscamera.app.data.model.User
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    
    // User Operations
    suspend fun createUser(user: User): Result<String> {
        return try {
            val userRef = firestore.collection("users").document(user.userId)
            userRef.set(user).await()
            Result.success(user.userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val snapshot = firestore.collection("users").document(userId).get().await()
            val user = snapshot.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Image Upload
    suspend fun uploadImage(imageUri: Uri, userId: String): Result<String> {
        return try {
            val fileName = "${UUID.randomUUID()}.jpg"
            val storageRef = storage.reference.child("images/$userId/$fileName")
            val uploadTask = storageRef.putFile(imageUri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            Result.success(downloadUrl.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Capture Data Operations
    suspend fun saveCapture(capture: CaptureData): Result<String> {
        return try {
            val captureRef = firestore.collection("captures").document(capture.captureId)
            captureRef.set(capture).await()
            Result.success(capture.captureId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCapturesByUser(userId: String): Result<List<CaptureData>> {
        return try {
            val snapshot = firestore.collection("captures")
                .whereEqualTo("userId", userId)
                .orderBy("createdAt")
                .get()
                .await()
            val captures = snapshot.documents.mapNotNull { it.toObject(CaptureData::class.java) }
            Result.success(captures)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getCurrentUserId(): String? = auth.currentUser?.uid
}
