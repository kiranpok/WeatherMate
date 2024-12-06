package com.example.weathermate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermate.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weatherData = repository.getWeatherData(latitude, longitude)
            // Update UI with weather data
        }
    }
}