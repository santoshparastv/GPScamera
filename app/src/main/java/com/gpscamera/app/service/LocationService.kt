package com.gpscamera.app.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import java.util.Locale

class LocationService(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    
    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10000L // 10 seconds
    ).apply {
        setMaxUpdateDelayMillis(5000L)
        setMinUpdateIntervalMillis(5000L)
    }.build()
    
    fun getCurrentLocation(): Flow<LocationResult> = callbackFlow {
        if (!hasLocationPermission()) {
            close(Exception("Location permission not granted"))
            return@callbackFlow
        }
        
        if (!isLocationEnabled()) {
            close(Exception("Location services are disabled"))
            return@callbackFlow
        }
        
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                trySend(result)
                close()
            }
        }
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            context.mainLooper
        )
        
        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }
    
    suspend fun getAddress(latitude: Double, longitude: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.getAddressLine(0) ?: "Address not available"
        } catch (e: IOException) {
            "Address not available"
        }
    }
    
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
