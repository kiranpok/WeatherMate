package com.example.weathermate.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.navigation.WeatherScreens
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.screens.favorites.FavoriteCityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun WeatherMateAppBar(
    title: String = "Title",
    icon: Painter? = null,
    isHomeScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteCityViewModel: FavoriteCityViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }

    if (showDialog.value) {
        SettingsDropDownMenu(
            showDialog = showDialog,
            expanded = expanded,
            navController = navController)
    }

    TopAppBar(
        title = {
            if (isHomeScreen)
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "location icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            Text(
                text = title,
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
        },
        actions = {
            if (isHomeScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
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
            } else Box {}
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
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "favorite icon",
                    tint = Color.White,
                    modifier = Modifier
                        .scale(0.9f)
                        .size(32.dp)
                        .clickable {
                            val dataList = title.split(",")
                            if (dataList.size < 2) {
                                // Debugging: Log invalid title format
                                Log.e("WeatherMateAppBar", "Invalid title format: $title")
                                Toast.makeText(context, "Invalid city format", Toast.LENGTH_SHORT).show()
                                return@clickable
                            }
                            val cityName = dataList[0]
                            val countryName = dataList[1]

                            favoriteCityViewModel.viewModelScope.launch {
                                try {
                                    // Log city name and start fetching process
                                    Log.d("WeatherMateAppBar", "Fetching temperature and weather condition for $cityName")

                                    // Fetch weather data
                                    val (temperature, weatherCondition) = withContext(Dispatchers.IO) { favoriteCityViewModel.getWeatherDetails(cityName) }

                                    //  logging to debug null values
                                    Log.d("WeatherMateAppBar", "Fetched temperature: $temperature")
                                    Log.d("WeatherMateAppBar", "Fetched weather condition: $weatherCondition")

                                    if (temperature == null || weatherCondition == null ) {
                                        Log.e("WeatherMateAppBar", "Temperature or weather condition is null for $cityName")
                                        Toast.makeText(context, "Error fetching weather data for $cityName", Toast.LENGTH_SHORT).show()
                                        return@launch
                                    }

                                    // Check if city already exists in favorites
                                    val existingCity = favoriteCityViewModel.getFavoriteCityById(cityName)
                                    if (existingCity != null) {
                                        Log.d("WeatherMateAppBar", "$cityName is already in the favorite list")
                                        Toast.makeText(context, "$cityName is already in your favorite list.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Log.d("WeatherMateAppBar", "Inserting $cityName into favorite list")
                                        favoriteCityViewModel.insertFavorite(
                                            FavoriteCity(
                                                city = cityName,
                                                country = countryName,
                                                temperature = temperature,
                                                weatherCondition = weatherCondition ?: ""
                                            )
                                        )
                                        Toast.makeText(context, "$cityName has been added to your favorite list.", Toast.LENGTH_SHORT).show()
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


// Dropdown menu for the Favorite, Settings, Alerts, and Feedback
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
                                    "Alerts" -> WeatherScreens.AlertsScreen.name
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
