package com.example.weathermate.network

import com.example.weathermate.model.Weather
import com.example.weathermate.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("appid") appKey: String = Constants.API_KEY,
        @Query("units") units:String = "metric", //Use metric for Celsius
        @Query("cnt") days: Int = 7

    ): Weather

}


