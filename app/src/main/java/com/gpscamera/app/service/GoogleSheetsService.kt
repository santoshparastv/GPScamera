package com.gpscamera.app.service

import android.content.Context
import com.gpscamera.app.data.model.CaptureData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Google Sheets API implementation (disabled - add dependencies later if needed)
// To enable: Add Google Sheets API dependencies to build.gradle
class GoogleSheetsService(private val context: Context) {
    
    suspend fun appendToSheet(
        spreadsheetId: String,
        sheetName: String,
        capture: CaptureData
    ): Result<Unit> = withContext(Dispatchers.IO) {
        // Google Sheets API disabled - add dependencies later if needed
        // To enable: 
        // 1. Uncomment Google Sheets dependencies in build.gradle
        // 2. Uncomment imports and implementation below
        Result.failure(UnsupportedOperationException(
            "Google Sheets export is disabled. " +
            "Add Google Sheets API dependencies to enable this feature."
        ))
    }
}
