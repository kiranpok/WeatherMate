package com.example.weathermate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Data class for FavoriteCity
@Entity(tableName = "favorites")
data class FavoriteCity(

    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "temperature")
    var temperature: Double? = null,

    @ColumnInfo(name = "weatherCondition")
    var weatherCondition: String? = null
)