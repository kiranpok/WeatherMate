package com.example.weathermate.utils

import com.example.weathermate.R

object ActivityMappingUtils {

    fun mapWeatherToActivity(weatherCondition: String, temperature: Double): Pair<String, Int> {
        return when {
            weatherCondition.lowercase() == "sunny" && temperature in 20.0..25.0 -> "Swimming" to R.drawable.ic_swimming
            weatherCondition.lowercase() == "sunny" && temperature in 25.0..35.0 -> "Swimming" to R.drawable.ic_swimming
            weatherCondition.lowercase() == "snow" && temperature < 0.0 -> "Skiing" to R.drawable.ic_skiing
            weatherCondition.lowercase() == "snow" && temperature in 0.0..5.0 -> "Snowboarding" to R.drawable.ic_snowboarding
            weatherCondition.lowercase() == "cloudy" && temperature in 10.0..20.0 -> "Running" to R.drawable.ic_running
            weatherCondition.lowercase() == "cloudy" && temperature in 20.0..30.0 -> "Cycling" to R.drawable.ic_cycling
            weatherCondition.lowercase() == "moderate rain" && temperature in 15.0..25.0 -> "Yoga" to R.drawable.ic_yoga
            weatherCondition.lowercase() == "moderate rain" && temperature < 15.0 -> "Gym" to R.drawable.ic_gym
            weatherCondition.lowercase() == "sky is clear" && temperature in 15.0..25.0 -> "Hiking" to R.drawable.ic_hiking
            weatherCondition.lowercase() == "sky is clear" && temperature in 25.0..35.0 -> "Walking" to R.drawable.ic_walking
            weatherCondition.lowercase() == "rain" && temperature in 10.0..20.0 -> "Workout" to R.drawable.ic_gym
            else -> "Reading" to R.drawable.ic_reading
        }
    }

    fun buildIconUrl(iconCode: String): String {
        return "https://openweathermap.org/img/wn/$iconCode.png"
    }
}