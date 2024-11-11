package com.example.weathermate.repository
import com.example.weathermate.data.WeatherDao
import com.example.weathermate.model.FavoriteCity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Repository class that interacts with the database (between the ViewModel and the DAO)
class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao){
    // Fetches the list of favorite cities from the database
    fun getFavoriteCities(): Flow<List<FavoriteCity>> {
        return weatherDao.getFavoriteCities()
    }

    // Inserts a favorite city into the database
    suspend fun insertFavoriteCity(city: FavoriteCity) {
        weatherDao.insertFavoriteCity(city)
    }

    // Updates a favorite city in the database
    suspend fun updateFavoriteCity(city: FavoriteCity) {
        weatherDao.updateFavoriteCity(city)
    }

    // Deletes a favorite city from the database
    suspend fun deleteFavoriteCity(city: FavoriteCity) {
        weatherDao.deleteFavoriteCity(city)
    }

    // Deletes all favorite cities from the database
    suspend fun deleteAllFavoriteCities() {
        weatherDao.deleteAllFavoriteCities()
    }

    // Fetches a favorite city by its name from the database
    suspend fun getFavoriteCityById(city: String): FavoriteCity {
        return weatherDao.getFavoriteCityById(city)
    }

}