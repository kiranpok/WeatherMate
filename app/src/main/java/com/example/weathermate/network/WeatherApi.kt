package com.example.weathermate.network

import com.example.weathermate.model.Weather
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // Fetching weather forecast for a city (by name)
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("cnt") count: Int = 7
    ): Weather

    // Fetching weather alerts (by city name)
    @GET("data/2.5/onecall/alerts")  // Changed endpoint to the appropriate one for weather alerts
    suspend fun getWeatherAlerts(
        @Query("q") city: String,
        @Query("appid") appid: String = Constants.API_KEY
    ): List<WeatherItem>

    // Fetching weather forecast for specific coordinates (latitude & longitude)
    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("cnt") count: Int = 7
    ): Weather

    // Fetching weather alerts (by coordinates)
    @GET("data/2.5/onecall/alerts")  // Same as above, changed endpoint
    suspend fun getWeatherAlertsByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid: String = Constants.API_KEY
    ): List<WeatherItem>
}
