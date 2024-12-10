package com.example.weathermate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermate.model.Weather
import com.example.weathermate.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState

    fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            val result = repository.getWeatherData(latitude, longitude)
            val aiInsights = repository.getAIInsights(result)
            _weatherState.value = WeatherState.Success(result, aiInsights)
        }
    }
}

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weather: Weather, val aiInsights: String) : WeatherState()
    data class Error(val message: String) : WeatherState()
}