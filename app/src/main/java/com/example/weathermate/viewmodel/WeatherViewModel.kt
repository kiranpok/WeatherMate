package com.example.weathermate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermate.model.Weather
import com.example.weathermate.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    // StateFlow to observe UI state
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState

    // Function to fetch weather data by coordinates
    fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            // Start by showing loading state
            _weatherState.value = WeatherState.Loading

            // Call repository to get the weather data
            val result = repository.getWeatherData(latitude, longitude)

            // Check if the result was successful or not
            if (result != null) {
                _weatherState.value = WeatherState.Success(result)
            } else {
                _weatherState.value = WeatherState.Error("Error fetching weather data")
            }
        }
    }
}

// Sealed class to represent the different states of the UI
sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weather: Weather) : WeatherState()
    data class Error(val message: String) : WeatherState()
}