package com.example.weathermate.repository

import android.util.Log
import com.example.weathermate.data.DataOrException
import com.example.weathermate.model.ActivityRecommendation
import com.example.weathermate.model.Weather
import com.example.weathermate.network.WeatherApi
import com.example.weathermate.utils.ActivityMappingUtils
import com.example.weathermate.utils.formatDate
import com.example.weathermate.utils.formatDecimals
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch (e: Exception) {
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")

        return DataOrException(data = response)
    }

    suspend fun getWeatherForecast(city: String): List<ActivityRecommendation> {
        val weatherData = getWeather(city, "metric").data ?: return emptyList()
        return weatherData.list.map { day ->
            val activity = ActivityMappingUtils.mapWeatherToActivity(day.weather[0].main, day.temp.day)
            val iconUrl = ActivityMappingUtils.buildIconUrl(day.weather[0].icon)
            val formattedDate = formatDate(day.dt)
            val formattedTemp = formatDecimals(day.temp.day)
            val weatherDescription = day.weather[0].description
            ActivityRecommendation(
                date = formattedDate,
                weatherType = weatherDescription,
                temperature = day.temp.day,
                suggestedActivity = activity.toString(),
                iconUrl = iconUrl
            )
        }
    }

    suspend fun getActivityRecommendations(city: String): DataOrException<List<ActivityRecommendation>, Boolean, Exception> {
        val result = DataOrException<List<ActivityRecommendation>, Boolean, Exception>(loading = true)
        try {
            val response = getWeatherForecast(city)
            result.data = response
            result.loading = false
        } catch (e: Exception) {
            Log.d("REX", "getActivityRecommendations: $e")
            result.e = e
            result.loading = false
        }
        Log.d("INSIDE", "getActivityRecommendations: ${result.data}")

        return result
    }
}