package com.example.weathermate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.weathermate.navigation.WeatherNavigation
import com.example.weathermate.screens.favorites.FavoriteCityViewModel
import com.example.weathermate.ui.theme.WeatherMateTheme
import com.example.weathermate.utils.LocationUtils
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val favoriteCityViewModel: FavoriteCityViewModel by viewModels()

    private var cityName by mutableStateOf<String?>(null)
    private var isLocationFetched by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WeatherMateTheme {
                WeatherMateApp(cityName, isLocationFetched)
            }
        }

        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                fetchLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            fetchLocation()
        } else {
            Log.e("MainActivity", "Location permission denied")
            cityName = "Permission Denied"
            isLocationFetched = true
            refreshUI()
        }
    }

    private fun fetchLocation() {
        val locationUtils = LocationUtils(this)
        locationUtils.getLastLocation { location, fetchedCityName ->
            if (location != null && fetchedCityName != null) {
                Log.d("MainActivity", "Location: $location, City: $fetchedCityName")
                cityName = fetchedCityName
            } else {
                Log.e("MainActivity", "Failed to fetch location or city name")
                cityName = "Location Unavailable"
            }
            isLocationFetched = true
            refreshUI()
        }
    }

    private fun refreshUI() {
        // No need to call setContent here; we rely on cityName and isLocationFetched being updated
    }
}

@Composable
fun WeatherMateApp(cityName: String? = null, isLocationFetched: Boolean = false) {
    WeatherMateTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherMateTheme {
        WeatherMateApp(cityName = "Preview City", isLocationFetched = true)
    }
}