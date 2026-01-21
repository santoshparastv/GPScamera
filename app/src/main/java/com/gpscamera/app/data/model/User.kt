package com.gpscamera.app.data.model

data class User(
    val userId: String = "",
    val name: String = "",
    val mobile: String = "",
    val email: String = "",
    val role: String = "user", // user or admin
    val createdAt: Long = System.currentTimeMillis()
)
