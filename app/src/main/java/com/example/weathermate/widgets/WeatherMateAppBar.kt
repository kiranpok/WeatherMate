package com.example.weathermate.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.navigation.WeatherScreens
import com.example.weathermate.screens.favorites.FavoriteCityViewModel
import com.example.weathermate.screens.settings.SettingsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@Composable
fun WeatherMateAppBar(
    title: String = "Title",
    icon: Painter? = null,
    isHomeScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteCityViewModel: FavoriteCityViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) } // Initialize location services
    val showDialog = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    var locationTitle by remember { mutableStateOf(title) } // Store the title for updating

    // Show Settings Dialog if needed
    if (showDialog.value) {
        SettingsDropDownMenu(
            showDialog = showDialog,
            expanded = expanded,
            navController = navController
        )
    }

    TopAppBar(
        modifier = Modifier.systemBarsPadding(),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    // Fetch user location when location icon is clicked
                    fetchUserLocation(context, fusedLocationClient) { city, country ->
                        if (city != null && country != null) {
                            val updatedTitle = "$city, $country"
                            locationTitle = updatedTitle // Update title with city and country
                            Toast.makeText(context, "Location updated to $updatedTitle", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to fetch location", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) {
                if (isHomeScreen)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_locate_me),
                        contentDescription = "location icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = locationTitle,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
        },
        actions = {
            if (isHomeScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = {
                    showDialog.value = !showDialog.value
                    expanded.value = !expanded.value
                    onButtonClicked.invoke()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "menu icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "back arrow",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onButtonClicked.invoke()
                        }
                )
            }

            if (isHomeScreen) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "favorite icon",
                    tint = Color.White,
                    modifier = Modifier
                        .scale(0.9f)
                        .size(32.dp)
                        .clickable {
                            val dataList = locationTitle.split(",")
                            if (dataList.size < 2) {
                                Log.e("WeatherMateAppBar", "Invalid title format: $locationTitle")
                                Toast.makeText(context, "Invalid city format", Toast.LENGTH_SHORT).show()
                                return@clickable
                            }
                            val cityName = dataList[0].trim()
                            val countryName = dataList[1].trim()

                            val unit = settingsViewModel.unitList.value.firstOrNull()
                                ?.unit?.split(" ")?.get(0)?.lowercase() ?: "metric"

                            favoriteCityViewModel.viewModelScope.launch {
                                try {
                                    val (temperature, weatherCondition) = withContext(Dispatchers.IO) {
                                        favoriteCityViewModel.getWeatherDetails(cityName)
                                    }

                                    val existingCity = favoriteCityViewModel.getFavoriteCityById(cityName)
                                    if (existingCity != null) {
                                        Toast.makeText(context, "$cityName is already in your favorite list.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        favoriteCityViewModel.insertFavorite(
                                            FavoriteCity(
                                                city = cityName,
                                                country = countryName,
                                                temperature = temperature,
                                                weatherCondition = weatherCondition
                                            )
                                        )
                                        Toast.makeText(context, "$cityName added to favorites.", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Log.e("WeatherMateAppBar", "Error saving favorite city: ${e.message}", e)
                                    Toast.makeText(context, "Error saving favorite city: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                )
            }
        },
        backgroundColor = Color(0xFF4C9EF1),
        elevation = elevation
    )
}

@SuppressLint("MissingPermission")
private fun fetchUserLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFetched: (city: String?, country: String?) -> Unit
) {
    // Fetch the user's last known location
    fusedLocationClient.lastLocation.addOnCompleteListener { task ->
        if (task.isSuccessful && task.result != null) {
            val location = task.result
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addressList.isNullOrEmpty()) {
                    val address = addressList[0]
                    val city = address.locality ?: address.subAdminArea
                    val country = address.countryName
                    onLocationFetched(city, country) // Provide city and country to callback
                } else {
                    onLocationFetched(null, null) // No address found
                }
            } catch (e: Exception) {
                Log.e("WeatherMateAppBar", "Geocoder failed: ${e.message}")
                onLocationFetched(null, null)
            }
        } else {
            Log.e("WeatherMateAppBar", "Failed to get location: ${task.exception}")
            onLocationFetched(null, null)
        }
    }
}

@Composable
fun SettingsDropDownMenu(
    showDialog: MutableState<Boolean>,
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    val items = listOf("Alerts", "Settings", "Favorite", "Feedback")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value, onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4C9EF1))
        ) {
            items.forEach { text ->
                DropdownMenuItem(onClick = {
                    expanded.value = false
                    showDialog.value = false
                }) {
                    Icon(
                        painter = when (text) {
                            "Favorite" -> painterResource(id = R.drawable.ic_favorite)
                            "Settings" -> painterResource(id = R.drawable.ic_settings)
                            "Alerts" -> painterResource(id = R.drawable.ic_alerts)
                            else -> painterResource(id = R.drawable.ic_feedback)
                        },
                        contentDescription = "menu icons",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = text,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "Favorite" -> WeatherScreens.FavoriteCityScreen.name
                                    "Settings" -> WeatherScreens.SettingsScreen.name
                                    "Alerts" -> "${WeatherScreens.WeatherAlertsScreen.name}/0.0/0.0/metric"
                                    else -> WeatherScreens.FeedbacksScreen.name
                                }
                            )
                        },
                        fontWeight = FontWeight.W300,
                        color = Color.White
                    )
                }
            }
        }
    }
}
