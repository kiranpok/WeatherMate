package com.example.weathermate.utils

import com.example.weathermate.model.WeatherItem
import java.text.SimpleDateFormat
import java.util.*

fun generateWeatherSummary(weatherItem: WeatherItem): String {
    val mainDescription = weatherItem.weather.firstOrNull()?.description ?: "clear sky"
    val temperature = weatherItem.temp.day.toInt()
    val minTemperature = weatherItem.temp.min.toInt()
    val maxTemperature = weatherItem.temp.max.toInt()
    val windSpeed = weatherItem.speed
    val humidity = weatherItem.humidity
    val rainVolume = weatherItem.rain ?: 0f
    val snowVolume = weatherItem.snow ?: 0f
    val sunrise = weatherItem.sunrise
    val sunset = weatherItem.sunset

    return buildString {
        append("Today's weather: $mainDescription. ")
        append("The temperature is around $temperature°C (low: $minTemperature°C, high: $maxTemperature°C). ")
        append("Humidity is $humidity%. Wind speed is ${"%.1f".format(windSpeed)} m/s. ")

        if (rainVolume > 0) append("Rainfall expected: ${"%.1f".format(rainVolume)} mm. ")
        if (snowVolume > 0) append("Snowfall expected: ${"%.1f".format(snowVolume)} mm. ")

        append("Sunrise is at ${formatUnixTime(sunrise)} and sunset is at ${formatUnixTime(sunset)}. ")

        if (temperature < 5) append("It's quite cold, dress warmly! ")
        if (windSpeed > 10) append("Be cautious of strong winds. ")
        if (mainDescription.contains("rain", true)) append("Don't forget an umbrella! ")
        if (mainDescription.contains("clear", true)) append("A perfect day for outdoor activities. ")
    }
}

fun formatUnixTime(unixTime: Int): String {
    val date = Date(unixTime * 1000L)
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date)
}