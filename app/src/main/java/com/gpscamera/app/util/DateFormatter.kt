package com.gpscamera.app.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    
    fun formatDate(date: Date): String = dateFormat.format(date)
    fun formatTime(date: Date): String = timeFormat.format(date)
}
