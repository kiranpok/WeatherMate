package com.example.weathermate.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCityViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {
    private val _favoriteList = MutableStateFlow<List<FavoriteCity>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteCities()
                .distinctUntilChanged()
                .collect { listOfFavoriteCities ->
                    if (listOfFavoriteCities.isEmpty()) {
                        Log.d("TAG", ": Empty Favorites")
                    } else {
                        val updatedList = listOfFavoriteCities.map { city ->
                            val (temperature, weatherCondition) = getWeatherDetails(city.city)
                            city.copy(
                                temperature = temperature,
                                weatherCondition = weatherCondition
                            )
                        }
                        _favoriteList.value = updatedList
                        Log.d("Favorites", ": ${favoriteList.value}")
                    }
                }
        }
    }

    fun insertFavorite(city: FavoriteCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteCity(city)
            Log.d("DatabaseOperation", "Inserted city: ${city.city}")
        }
    }

    fun updateFavorite(city: FavoriteCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCity(city)
            Log.d("DatabaseOperation", "Updated city: ${city.city}")
        }
    }

    suspend fun getFavoriteCityById(city: String): FavoriteCity? {
        return repository.getFavoriteCityById(city)
    }

    fun addCityAsFavorite(cityName: String, country: String) {
        viewModelScope.launch {
            val (temperature, condition) = getWeatherDetails(cityName)
            val favoriteCity = FavoriteCity(
                city = cityName,
                country = country,
                temperature = temperature,
                weatherCondition = condition,
            )
            insertFavorite(favoriteCity)
        }
    }


    fun deleteFavorite(city: FavoriteCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteCity(city)
            Log.d("DatabaseOperation", "Deleted city: ${city.city}")
        }
    }

    suspend fun getWeatherDetails(cityName: String): Pair<Double, String> {
        return try {
            val weatherResponse = repository.fetchWeatherData(cityName)
            val weatherData = weatherResponse?.list?.firstOrNull()
            val temperature = weatherData?.temp?.day ?: 0.0
            val weatherCondition = weatherData?.weather?.firstOrNull()?.description ?: "Unknown"
            Log.d(
                "WeatherDetailsFetch",
                "Fetched weather data for $cityName: Temp = $temperature, Condition = $weatherCondition"
            )
            Pair(temperature, weatherCondition)
        } catch (e: Exception) {
            Log.e(
                "WeatherDetailsFetch",
                "Error fetching weather data for $cityName: ${e.message}",
                e
            )
            // Returning default values in case of failure
            Pair(0.0, "")
        }
    }

}