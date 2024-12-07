package com.example.weathermate.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import java.util.Locale

class LocationUtils(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLastLocation(callback: (Location?, String?) -> Unit) {
        requestNewLocationData(callback)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(callback: (Location?, String?) -> Unit) {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                Log.d("LocationUtils", "Location received: $location")
                val cityName = location?.let { getCityName(it.latitude, it.longitude) }
                Log.d("LocationUtils", "City name: $cityName")
                callback(location, cityName)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }, Looper.getMainLooper())
    }

    private fun getCityName(latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].locality
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("LocationUtils", "Geocoder failed: ${e.message}")
            null
        }
    }
}