package com.example.weathermate.utils

import com.example.weathermate.R

object ActivityMappingUtils {

    fun mapWeatherToActivity(weatherCondition: String): Pair<String, Int> {
        return when (weatherCondition.lowercase()) {
            "sunny" -> "Swimming" to R.drawable.ic_swimming
            "snow" -> "Skating" to R.drawable.ic_skating
            "cloudy" -> "Yoga" to R.drawable.ic_yoga
            "moderate rain" -> "Gym" to R.drawable.ic_gym
            "sky is clear" -> "Reading" to R.drawable.ic_reading
            else -> "Walking" to R.drawable.ic_walking
        }
    }

    fun buildIconUrl(iconCode: String): String {
        return "https://openweathermap.org/img/wn/$iconCode.png"
    }
}