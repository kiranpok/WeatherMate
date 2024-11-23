package com.example.weathermate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.weathermate.navigation.WeatherNavigation
import com.example.weathermate.screens.favorites.FavoriteCityViewModel
import com.example.weathermate.ui.theme.WeatherMateTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val favoriteCityViewModel: FavoriteCityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the content fully immersive by allowing the app to draw behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WeatherMateTheme {
                WeatherMateApp()
            }
        }
    }
}

@Composable
fun WeatherMateApp() {
    WeatherMateTheme {
        // Surface to provide consistent background color
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Center content and manage navigation
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherNavigation()
            }
        }
    }
}


