package com.example.weathermate.network

import com.example.weathermate.model.Weather
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query(value = "q") query: String,
        @Query(value = "units") units: String = "metric",
        @Query(value = "appid") appid: String = Constants.API_KEY,
        @Query("cnt") count: Int = 7

    ): Weather

    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherAlerts(
        @Query("q") city: String,
        @Query("appid") appid: String = Constants.API_KEY,
    ): List<WeatherItem>

}



