package com.gpscamera.app.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import java.text.SimpleDateFormat
import java.util.*

object ImageWatermark {
    fun addWatermark(
        bitmap: Bitmap,
        userName: String,
        inputId: String,
        latitude: Double,
        longitude: Double,
        dateTime: Date
    ): Bitmap {
        val result = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        
        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 40f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            setShadowLayer(3f, 2f, 2f, Color.BLACK)
        }
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateTimeStr = dateFormat.format(dateTime)
        val locationStr = String.format(Locale.getDefault(), "%.6f, %.6f", latitude, longitude)
        
        val lines = listOf(
            "User: $userName",
            "ID: $inputId",
            "Location: $locationStr",
            "Date/Time: $dateTimeStr"
        )
        
        val lineHeight = paint.textSize * 1.2f
        val padding = 20f
        var y = padding + paint.textSize
        
        lines.forEach { line ->
            canvas.drawText(line, padding, y, paint)
            y += lineHeight
        }
        
        return result
    }
}
