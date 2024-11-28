package com.example.weathermate.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.weathermate.data.WeatherDao
import com.example.weathermate.data.WeatherDatabase
import com.example.weathermate.network.WeatherApi
import com.example.weathermate.repository.WeatherRepository
import com.example.weathermate.screens.alerts.WeatherAlertService
import com.example.weathermate.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase {
        val dbPath = context.getDatabasePath("weather_database").absolutePath
        Log.d("WeatherDatabase", "Database initialized at: $dbPath")

        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherAlertService(): WeatherAlertService {
        return WeatherAlertService()
    }

    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        weatherAlertService: WeatherAlertService,
    ): WeatherRepository {
        return WeatherRepository(api, weatherAlertService)
    }



}