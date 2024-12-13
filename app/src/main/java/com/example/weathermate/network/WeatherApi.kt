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
        @Query("q") query: String,          // City name
        @Query("units") units: String = "metric",  // Units of measurement (metric/imperial)
        @Query("appid") appid: String = Constants.API_KEY,  // API key for authentication
        @Query("cnt") count: Int = 7        // Number of days in the forecast
    ): Weather

    // Fetching weather alerts (by city name)
    @GET("data/2.5/onecall/alerts")  // Changed endpoint to the appropriate one for weather alerts
    suspend fun getWeatherAlerts(
        @Query("q") city: String,     // City name
        @Query("appid") appid: String = Constants.API_KEY  // API key for authentication
    ): List<WeatherItem>

    // Fetching weather forecast for specific coordinates (latitude & longitude)
    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,     // Latitude of the location
        @Query("lon") longitude: Double,    // Longitude of the location
        @Query("units") units: String = "metric",  // Units of measurement (metric/imperial)
        @Query("appid") appid: String = Constants.API_KEY,  // API key for authentication
        @Query("cnt") count: Int = 7        // Number of days in the forecast
    ): Weather

    // Fetching weather alerts (by coordinates)
    @GET("data/2.5/onecall/alerts")  // Same as above, changed endpoint
    suspend fun getWeatherAlertsByCoordinates(
        @Query("lat") latitude: Double,     // Latitude of the location
        @Query("lon") longitude: Double,    // Longitude of the location
        @Query("appid") appid: String = Constants.API_KEY  // API key for authentication
    ): List<WeatherItem>
}