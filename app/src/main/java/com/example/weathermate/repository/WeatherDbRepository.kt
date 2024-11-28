package com.example.weathermate.repository
import android.util.Log
import com.example.weathermate.data.WeatherDao
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.model.Unit
import com.example.weathermate.model.Weather
import com.example.weathermate.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Repository class that interacts with the database (between the ViewModel and the DAO)
class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao, private val apiService: WeatherApi) {
    // Fetches the list of favorite cities from the database
    fun getFavoriteCities(): Flow<List<FavoriteCity>> {
        return weatherDao.getFavoriteCities()
    }

    // Inserts a favorite city into the database
    suspend fun insertFavoriteCity(city: FavoriteCity) {
        Log.d("WeatherDbRepository", "Inserting city: ${city.city}")
        weatherDao.insertFavoriteCity(city)
    }

    // Updates a favorite city in the database
    suspend fun updateFavoriteCity(city: FavoriteCity) {
        Log.d("WeatherDbRepository", "Updating city: ${city.city}")
        weatherDao.updateFavoriteCity(city)
    }

    // Deletes a favorite city from the database
    suspend fun deleteFavoriteCity(city: FavoriteCity) {
        Log.d("WeatherDbRepository", "Deleting city: ${city.city}")
        weatherDao.deleteFavoriteCity(city)
    }

    // Deletes all favorite cities from the database
    suspend fun deleteAllFavoriteCities() {
        weatherDao.deleteAllFavoriteCities()
    }

    // Fetches a favorite city by its name from the database
    suspend fun getFavoriteCityById(city: String): FavoriteCity? {
        val favoriteCity = weatherDao.getFavoriteCityById(city)
        Log.d("WeatherDbRepository", "Fetched city: $favoriteCity")
        return favoriteCity
    }

    // Fetches the temperature of a specific city from the database
    suspend fun getTemperature(city: String): Double? {
        val temperature = weatherDao.getTemperature(city)
        Log.d("WeatherDbRepository", "Fetched temperature for $city: $temperature")
        return temperature
    }

    // Fetches the weather condition of a specific city from the database
    suspend fun getWeatherCondition(city: String): String? {
        val weatherCondition = weatherDao.getWeatherCondition(city)
        Log.d("WeatherDbRepository", "Fetched weather condition for $city: $weatherCondition")
        return weatherCondition
    }

    // Fetches weather data from the API
    suspend fun fetchWeatherData(cityName: String): Weather? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWeather(cityName)

                response

            } catch (e: Exception) {
                Log.e("WeatherDbRepository", "Error fetching weather data: ${e.message}", e)
                null
            }
        }

    }

    // Unit (Settings) Management
    fun getUnits(): Flow<List<Unit>> {
        return weatherDao.getUnits()
    }

    suspend fun insertUnit(unit: Unit) {
        Log.d("WeatherDbRepository", "Inserting or updating unit: ${unit}")
        weatherDao.insertUnit(unit)
    }

    suspend fun updateUnit(unit: Unit) {
        Log.d("WeatherDbRepository", "Updating unit: ${unit}")
        weatherDao.updateUnit(unit)
    }

    suspend fun deleteUnit(unit: Unit) {
        Log.d("WeatherDbRepository", "Deleting unit: ${unit}")
        weatherDao.deleteUnit(unit)
    }

    suspend fun deleteAllUnits() {
        Log.d("WeatherDbRepository", "Deleting all units")
        weatherDao.deleteAllUnits()
    }

    suspend fun getCurrentUnit(): Unit? {
        return withContext(Dispatchers.IO) {
            val units = weatherDao.getUnits().firstOrNull() // Get first emission
            units?.firstOrNull() // Return the first unit if exists
        }
    }
}
