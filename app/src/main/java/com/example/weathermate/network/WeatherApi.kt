package com.example.weathermate.network

import com.example.weathermate.model.Weather
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("cnt") count: Int = 7
    ): Weather

    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherAlerts(
        @Query("q") city: String,
        @Query("appid") appid: String = Constants.API_KEY
    ): List<WeatherItem>

    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("cnt") count: Int = 7
    ): Weather

    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherAlertsByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid: String = Constants.API_KEY
    ): List<WeatherItem>
}