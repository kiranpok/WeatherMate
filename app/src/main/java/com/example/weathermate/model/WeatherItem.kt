package com.example.weathermate.model

data class WeatherItem(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    val feels_like: FeelsLike,
    val gust: Double,
    val humidity: Int,
    val pop: Float,  //change int to float
    val pressure: Int,
    val rain: Float?, //double to float
    val wind: Double,
    val snow: Float?,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<WeatherObject>
)