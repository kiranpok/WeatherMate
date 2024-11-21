package com.example.weathermate.model

data class ActivityRecommendation(
    val date: String,
    val weatherType: String,
    val temperature: Double,
    val suggestedActivity: String,
    val iconUrl: String
)