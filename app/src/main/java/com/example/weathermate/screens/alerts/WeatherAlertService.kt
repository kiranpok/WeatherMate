package com.example.weathermate.screens.alerts

import android.util.Log
import com.example.weathermate.model.WeatherItem
import javax.inject.Inject

class WeatherAlertService @Inject constructor() {
    private val maxTemp = 35.0 // Celsius
    private val nightTemp = 0.0 // Celsius
    private val minTemp = 5.0 // Celsius
    private val rainThreshold = 50.0 // mm
    private val snowThreshold = 10.0 // mm
    private val windThreshold = 50.0 // km/h
    private val humidityThreshold = 80 // %

    // Checks for specific weather alerts
    fun checkWeatherAlerts(weatherItem: WeatherItem): String {
        val tempDay = weatherItem.temp.day
        val tempNight = weatherItem.temp.night
        val tempMorn = weatherItem.temp.morn

        // Check temperature alerts
        if (tempDay > maxTemp) return getAlertMessage("Heat", "Daytime temperature exceeds $maxTemp°C")
        if (tempNight < nightTemp) return getAlertMessage("Frost", "Nighttime temperature drops below $nightTemp°C")
        if (tempMorn < minTemp) return getAlertMessage("Cold", "Morning temperature below $minTemp°C")

        // Check rain alert (handle nullable rain value)
        if (weatherItem.rain?.let { it > rainThreshold } == true) {
            return "Heavy rain alert: More than $rainThreshold mm of rain!"
        }

        // Check snow alert (handle nullable snow value)
        if (weatherItem.snow?.let { it > snowThreshold } == true) {
            return "Heavy snow alert: More than $snowThreshold mm of snow!"
        }

        // Check wind alert (convert speed from m/s to km/h and compare)
        if (weatherItem.speed * 3.6 > windThreshold) {
            return "Wind alert: Wind speed exceeds $windThreshold km/h!"
        }

        // Check humidity alert
        if (weatherItem.humidity > humidityThreshold) {
            return "Humidity alert: Humidity above $humidityThreshold%!"
        }

        // Check general weather conditions
        return getWeatherAlert(weatherItem)
    }

    // A helper method to standardize alert messages
    fun getAlertMessage(type: String, message: String): String {
        return "$type alert: $message"
    }

    private fun getWeatherAlert(weatherItem: WeatherItem): String {
        val alerts = mutableListOf<String>()

        for (weatherCondition in weatherItem.weather) {
            when (weatherCondition.main) {
                "Clear" -> alerts.add("Weather Info: Sunny conditions expected!")

                "Rain" -> {
                    val rainAmount = weatherItem.rain ?: 0f
                    if (rainAmount > 10) {
                        alerts.add("Heavy Rain Alert: Over $rainAmount mm of rain expected!")
                    } else {
                        alerts.add("Weather Info: Light to moderate rain expected.")
                    }
                }

                "Snow" -> {
                    val snowAmount = weatherItem.snow ?: 0f
                    if (snowAmount > 10) {
                        alerts.add("Heavy Snow Alert: Over $snowAmount cm of snow expected!")
                    } else {
                        alerts.add("Weather Info: Light snow expected.")
                    }
                }

                "Clouds" -> {
                    val cloudCoverage = weatherItem.clouds
                    if (cloudCoverage > 90) {
                        alerts.add("Weather Info: Overcast clouds expected.")
                    } else {
                        alerts.add("Weather Info: Partly cloudy skies.")
                    }
                }

                "Thunderstorm" -> alerts.add("Thunderstorm Alert: Stormy weather ahead!")

                "Fog" -> alerts.add("Fog Alert: Low visibility conditions expected!")

                else -> alerts.add("Weather Info: Conditions seem normal.")
            }
        }

        return if (alerts.isNotEmpty()) alerts.joinToString("\n") else "Weather Info: Conditions seem normal."
    }


    // Checks alerts for a list of weather items and removes duplicates
    fun checkWeatherAlertsForList(weatherList: List<WeatherItem>): List<String> {
        Log.d("WeatherAlertService", "Processing weather list: $weatherList")

        // Collect all alerts and filter duplicates
        return weatherList.mapNotNull { weatherItem ->
            val alert = checkWeatherAlerts(weatherItem)
            if (alert != "Weather Info: Conditions seem normal.") alert else null
        }.distinct()
    }
}
