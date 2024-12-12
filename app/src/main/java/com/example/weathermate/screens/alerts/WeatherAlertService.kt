package com.example.weathermate.screens.alerts

import android.util.Log
import com.example.weathermate.model.WeatherItem
import javax.inject.Inject

class WeatherAlertService @Inject constructor() {
    // Define thresholds in Celsius
    private val maxTempCelsius = 35.0
    private val nightTempCelsius = 0.0
    private val minTempCelsius = 5.0
    private val rainThreshold = 50.0 // mm
    private val snowThreshold = 10.0 // mm
    private val windThreshold = 50.0 // km/h
    private val humidityThreshold = 80 // %


    // Converts Celsius to Fahrenheit
    private fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9 / 5) + 32

    // Converts Fahrenheit to Celsius
    private fun fahrenheitToCelsius(fahrenheit: Double): Double = (fahrenheit - 32) * 5 / 9

    // Determines whether to use Celsius or Fahrenheit thresholds
    private fun getThresholds(isCelsius: Boolean): Map<String, Double> {
        return if (isCelsius) {
            mapOf(
                "maxTemp" to maxTempCelsius,
                "nightTemp" to nightTempCelsius,
                "minTemp" to minTempCelsius
            )
        } else {
            mapOf(
                "maxTemp" to celsiusToFahrenheit(maxTempCelsius),
                "nightTemp" to celsiusToFahrenheit(nightTempCelsius),
                "minTemp" to celsiusToFahrenheit(minTempCelsius)
            )
        }
    }


    // Checks for specific weather alerts
    fun checkWeatherAlerts(weatherItem: WeatherItem, isCelsius: Boolean): String {
        val thresholds = getThresholds(isCelsius)
        val tempDay = weatherItem.temp.day
        val tempNight = weatherItem.temp.night
        val tempMorn = weatherItem.temp.morn

        // Check temperature alerts
        if (tempDay > thresholds["maxTemp"]!!) {
            return getAlertMessage("Heat", "Daytime temperature exceeds ${thresholds["maxTemp"]}${if (isCelsius) "°C" else "°F"}")
        }
        if (tempNight < thresholds["nightTemp"]!!) {
            return getAlertMessage("Frost", "Nighttime temperature drops below ${thresholds["nightTemp"]}${if (isCelsius) "°C" else "°F"}")
        }
        if (tempMorn < thresholds["minTemp"]!!) {
            return getAlertMessage("Cold", "Morning temperature below ${thresholds["minTemp"]}${if (isCelsius) "°C" else "°F"}")
        }

        // Check rain alert
        if (weatherItem.rain?.let { it > rainThreshold } == true) {
            return "Heavy rain alert: More than $rainThreshold mm of rain!"
        }

        // Check snow alert
        if (weatherItem.snow?.let { it > snowThreshold } == true) {
            return "Heavy snow alert: More than $snowThreshold mm of snow!"
        }

        // Check wind alert
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
    fun checkWeatherAlertsForList(weatherList: List<WeatherItem>, isCelsius: Boolean): List<String> {
        Log.d("WeatherAlertService", "Processing weather list: $weatherList")

        // Collect all alerts and filter duplicates
        return weatherList.mapNotNull { weatherItem ->
            val alert = checkWeatherAlerts(weatherItem, isCelsius)
            if (alert != "Weather Info: Conditions seem normal.") alert else null
        }.distinct()
    }
}