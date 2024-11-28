package com.example.weathermate.screens.alerts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermate.data.DataOrException
import com.example.weathermate.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherAlertViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherAlertService: WeatherAlertService
) : ViewModel() {

    private val _weatherAlerts = MutableStateFlow<DataOrException<List<String>, Boolean, Exception>>(DataOrException())
    val weatherAlerts: StateFlow<DataOrException<List<String>, Boolean, Exception>> get() = _weatherAlerts

    // Fetch weather alerts using city name
    fun fetchWeatherAlertsByCity(city: String, units: String) {
        viewModelScope.launch {
            try {
                _weatherAlerts.value = DataOrException(loading = true) // Loading state

                // Call repository method that fetches weather by city
                val result = repository.getWeatherAndAlertsByCity(city, units)

                // Extract weather items from the result
                val weatherItems = result.data?.list

                // If weather items are available, process them to generate alerts
                if (weatherItems != null && weatherItems.isNotEmpty()) {
                    val alerts = weatherAlertService.checkWeatherAlertsForList(weatherItems)
                    if (alerts.isNotEmpty()) {
                        // If there are alerts, update the state with the alerts
                        _weatherAlerts.value = DataOrException(data = alerts)
                    } else {
                        // If no alerts, set a message indicating no significant weather
                        _weatherAlerts.value = DataOrException(data = listOf("No significant weather alerts"))
                    }
                } else {
                    _weatherAlerts.value = DataOrException(data = listOf("No weather data available"))
                }
            } catch (e: Exception) {
                // Handle error if there's an issue with the API call
                _weatherAlerts.value = DataOrException(e = e)
            }
        }
    }
}
