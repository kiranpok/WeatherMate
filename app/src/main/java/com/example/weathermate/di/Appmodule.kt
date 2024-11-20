package com.example.weathermate.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.weathermate.data.WeatherDao
import com.example.weathermate.data.WeatherDatabase
import com.example.weathermate.network.WeatherApi


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.weathermate.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //  Provides an instance of the WeatherDao from the WeatherDatabase
    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }

    //  Provides an instance of the WeatherDatabase
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase {

        // Log the database file path for debugging purposes
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

}