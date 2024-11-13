package com.example.weathermate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.navigation.WeatherNavigation
import com.example.weathermate.screens.favorites.FavoriteCityViewModel
import com.example.weathermate.ui.theme.WeatherMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val favoriteCityViewModel: FavoriteCityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherMateApp()
        }
        /**
        // Trigger operations sequentially
        favoriteCityViewModel.viewModelScope.launch {

            val city = FavoriteCity(city = "Helsinki", country = "Finland")
            favoriteCityViewModel.insertFavorite(city)
            delay(1000) // Wait for 1 second to simulate time between operations

            val updatedCity = FavoriteCity(city = "Helsinki", country = "Updated Finland")
            favoriteCityViewModel.updateFavorite(updatedCity)
            delay(1000)

            favoriteCityViewModel.deleteFavorite(updatedCity)
        }
        */

    }
}

@Composable
fun WeatherMateApp() {
    WeatherMateTheme {
        Surface(color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
                .systemBarsPadding()) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherMateTheme {
        WeatherMateApp()
    }
}
