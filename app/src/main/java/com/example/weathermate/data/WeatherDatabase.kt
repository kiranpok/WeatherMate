package com.example.weathermate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.model.Unit

// Sets up the Room database for the app
@Database(entities = [FavoriteCity::class, Unit::class], version = 4, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    // Provides the DAO for database operations
    abstract fun weatherDao(): WeatherDao
}