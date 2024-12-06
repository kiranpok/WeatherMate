package com.example.weathermate.repository

import android.util.Log
import com.example.weathermate.data.DataOrException
import com.example.weathermate.model.ActivityRecommendation
import com.example.weathermate.model.Weather
import com.example.weathermate.network.WeatherApi
import com.example.weathermate.screens.alerts.WeatherAlertService
import com.example.weathermate.utils.ActivityMappingUtils
import com.example.weathermate.utils.Constants
import com.example.weathermate.utils.formatDate
import com.example.weathermate.utils.formatDecimals
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val weatherAlertService: WeatherAlertService,
) {

    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            Log.d("WeatherRepository", "Requesting weather for: $cityQuery with units: $units")
            api.getWeather(query = cityQuery, units = units)
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather: ${e.message}")
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")

        return DataOrException(data = response)
    }

    suspend fun getWeatherByCoordinates(latitude: Double, longitude: Double, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            Log.d("WeatherRepository", "Requesting weather for coordinates: ($latitude, $longitude) with units: $units")
            api.getWeatherByCoordinates(latitude, longitude, units)
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather: ${e.message}")
            return DataOrException(e = e)
        }
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

    suspend fun getWeatherAndAlertsByCity(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val dataOrException = DataOrException<Weather, Boolean, Exception>()
        try {
            val weatherResponse = api.getWeather(city, units, Constants.API_KEY)
            dataOrException.data = weatherResponse
            val weatherAlerts = api.getWeatherAlerts(city, Constants.API_KEY)
            weatherResponse.list.forEach { weatherItem ->
                weatherItem.weather.forEach { weatherObject ->
                    val alerts = weatherAlertService.checkWeatherAlertsForList(weatherResponse.list)
                }
            }
        } catch (e: Exception) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getWeatherData(latitude: Double, longitude: Double): Weather {
        return api.getWeatherByCoordinates(latitude, longitude, "metric")
    }
}