package com.example.weathermate.screens.main


import com.example.weathermate.data.DataOrException
import com.example.weathermate.model.Weather
import com.example.weathermate.repository.WeatherRepository
import androidx.lifecycle.ViewModel
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.screens.alerts.WeatherAlertService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherAlertService: WeatherAlertService

    ) : ViewModel() {
    // Function to get weather data for a given city units in metric or imperial
    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }

    // Function to get weather alerts based on the weather list and the unit type
    fun getWeatherAlerts(weatherList: List<WeatherItem>, isCelsius: Boolean): List<String> {
        return weatherAlertService.checkWeatherAlertsForList(weatherList, isCelsius)
    }

}