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

// ViewModel class that interacts with the repository to manage the favorite cities
@HiltViewModel
class FavoriteCityViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel(){
    // Mutable state flow to hold the list of favorite cities
    private val _favoriteList = MutableStateFlow<List<FavoriteCity>>(emptyList())
    // Immutable state flow to expose the list of favorite cities
    val favoriteList = _favoriteList.asStateFlow()

    // Initialize the favorite cities list
    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Collect the list of favorite cities from the repository
            repository.getFavoriteCities().distinctUntilChanged()
                .collect { listOfFavoriteCities ->
                    if (listOfFavoriteCities.isEmpty()) {
                        // Message if the list of favorite cities is empty
                        Log.d("TAG", ": Empty Favorites")

                    } else {
                        // Update the list of favorite cities if not empty
                        _favoriteList.value = listOfFavoriteCities
                        Log.d("Favorites", ": ${favoriteList.value}")
                    }
                }
        }
    }

    // Function to insert a favorite city
    fun insertFavorite(city: FavoriteCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteCity(city)
            Log.d("DatabaseOperation", "Inserted city: ${city.city}")
        }
    }

    // Function to update a favorite city
    fun updateFavorite(city: FavoriteCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCity(city)
            Log.d("DatabaseOperation", "Updated city: ${city.city}")
        }
    }

    // Function to get favorite city by id
    suspend fun getFavoriteCityById(city: String): FavoriteCity {
        return repository.getFavoriteCityById(city)
    }

    // Function to delete a favorite city
    fun deleteFavorite(city: FavoriteCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteCity(city)
            Log.d("DatabaseOperation", "Deleted city: ${city.city}")
        }
    }

}