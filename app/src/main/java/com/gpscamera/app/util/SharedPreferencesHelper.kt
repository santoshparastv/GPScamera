package com.gpscamera.app.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "gps_camera_prefs",
        Context.MODE_PRIVATE
    )
    
    fun saveUserId(userId: String) {
        prefs.edit().putString("user_id", userId).apply()
    }
    
    fun getUserId(): String? {
        return prefs.getString("user_id", null)
    }
    
    fun saveUserName(userName: String) {
        prefs.edit().putString("user_name", userName).apply()
    }
    
    fun getUserName(): String? {
        return prefs.getString("user_name", null)
    }
    
    fun clearUserData() {
        prefs.edit().clear().apply()
    }
    
    fun saveGoogleSheetId(sheetId: String) {
        prefs.edit().putString("google_sheet_id", sheetId).apply()
    }
    
    fun getGoogleSheetId(): String? {
        return prefs.getString("google_sheet_id", null)
    }
}
