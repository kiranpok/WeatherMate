package com.example.weathermate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Annotation the application class with @HiltAndroidApp to enable Hilt in the application
@HiltAndroidApp
class WeatherApplication : Application() {
    override fun onCreate() {
        //Initialization code for the application can be added here
        super.onCreate()
    }
}