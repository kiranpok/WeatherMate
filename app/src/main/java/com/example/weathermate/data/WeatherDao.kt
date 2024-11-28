package com.example.weathermate.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.model.Unit
import kotlinx.coroutines.flow.Flow
import kotlin.Unit as Unit1

// DAO interface for accessing favorites table in the database
@Dao
interface WeatherDao {
    // Get all cities from the 'favorites' table
    @Query(value = "SELECT * FROM favorites")
    fun getFavoriteCities(): Flow<List<FavoriteCity>>

    // Get a specific city from the 'favorites' table
    @Query(value = "SELECT * FROM favorites WHERE city = :city")
    suspend fun getFavoriteCityById(city: String): FavoriteCity?

    // Insert a city into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCity(city: FavoriteCity)

    // Update a city in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavoriteCity(city: FavoriteCity)

    // Delete a specific city from the database
    @Delete
    suspend fun deleteFavoriteCity(city: FavoriteCity)

    // Clear all cities from the 'favorites' table
    @Query(value = "DELETE FROM favorites")
    suspend fun deleteAllFavoriteCities()

    // get temperature of a specific city
    @Query("SELECT temperature FROM favorites WHERE city = :city")
    suspend fun getTemperature(city: String): Double?

    // get weather condition of a specific city
    @Query(value = "SELECT weatherCondition FROM favorites WHERE city = :city")
    suspend fun getWeatherCondition(city: String): String?


    //Unit table
    @Query(value = "SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query(value = "DELETE from settings_tbl")
    suspend fun deleteAllUnits()

    // Delete a specific unit from the database
    @Delete
    suspend fun deleteUnit(city: Unit)


}