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

    // Common function for making API calls
    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): DataOrException<T, Boolean, Exception> {
        return try {
            DataOrException(data = apiCall())
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error: ${e.message}")
            DataOrException(e = e)
        }
    }

    // Get current weather data by city name
    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return safeApiCall { api.getWeather(query = cityQuery, units = units) }
    }

    // Get current weather data by coordinates
    suspend fun getWeatherByCoordinates(latitude: Double, longitude: Double, units: String): DataOrException<Weather, Boolean, Exception> {
        return safeApiCall { api.getWeatherByCoordinates(latitude, longitude, units) }
    }

    // Get weather forecast and map it to activity recommendations
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

    // Get activity recommendations based on weather forecast
    suspend fun getActivityRecommendations(city: String): DataOrException<List<ActivityRecommendation>, Boolean, Exception> {
        val result = DataOrException<List<ActivityRecommendation>, Boolean, Exception>(loading = true)
        try {
            val response = getWeatherForecast(city)
            result.data = response
            result.loading = false
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching activity recommendations: ${e.message}")
            result.e = e
            result.loading = false
        }
        return result
    }

    // Get weather and alerts by city, this method is more complete now
    suspend fun getWeatherAndAlertsByCity(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val dataOrException = DataOrException<Weather, Boolean, Exception>()
        try {
            val weatherResponse = api.getWeather(city, units, Constants.API_KEY)
            dataOrException.data = weatherResponse
            val weatherAlerts = api.getWeatherAlerts(city, Constants.API_KEY)

            // Process weather alerts
            weatherResponse.list.forEach { weatherItem ->
                weatherItem.weather.forEach { weatherObject ->
                    // Check if there are weather alerts for the current weather item
                    weatherAlertService.checkWeatherAlertsForList(weatherResponse.list, units == "metric")
                }
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather and alerts: ${e.message}")
            dataOrException.e = e
        }
        return dataOrException
    }

    // Get weather data by coordinates
    suspend fun getWeatherData(latitude: Double, longitude: Double): Weather {
        return api.getWeatherByCoordinates(latitude, longitude, "metric")
    }
}