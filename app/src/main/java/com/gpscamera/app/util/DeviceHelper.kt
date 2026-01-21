package com.gpscamera.app.util

import android.content.Context
import android.provider.Settings
import java.util.UUID

object DeviceHelper {
    fun getDeviceId(context: Context): String {
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        return UUID.nameUUIDFromBytes(androidId.toByteArray()).toString()
    }
}
