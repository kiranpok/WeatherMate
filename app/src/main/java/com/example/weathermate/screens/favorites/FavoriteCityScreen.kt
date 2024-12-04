package com.example.weathermate.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.navigation.WeatherScreens
import com.example.weathermate.widgets.WeatherMateAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.weathermate.screens.settings.SettingsViewModel

// display Favorite City Screen
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteCityScreen(
    navController: NavController,
    favoriteCityViewModel: FavoriteCityViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            WeatherMateAppBar(
                title = "Favorite Cities",
                icon = painterResource(id = R.drawable.ic_back_arrow),
                isHomeScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(contentPadding)
                    .background(Color(0xFF4C9EF1))
            ) {
                val list = favoriteCityViewModel.favoriteList.collectAsState().value
                LazyColumn {
                    // Display each favorite city in a row and allow swipe to delete
                    items(items = list, key = { it.city }) { favorite ->

                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    favoriteCityViewModel.deleteFavorite(favorite)
                                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
                                }
                                true
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = {
                                val color = when (dismissState.dismissDirection) {
                                    DismissDirection.EndToStart -> Color.Red
                                    else -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                CityRow(favorite, navController = navController, favoriteCityViewModel)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: FavoriteCity,
    navController: NavController,
    favoriteCityViewModel: FavoriteCityViewModel
) {
    val mappedCondition = mapWeatherCondition(favorite.weatherCondition ?: "Unknown")


    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                navController.navigate(WeatherScreens.HomeScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(CornerSize(12.dp)),
        color = Color(0xFF2196F3)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = favorite.city,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = favorite.weatherCondition ?: "Unknown",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = getWeatherIcon(mappedCondition)),
                contentDescription = "weather icon",
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 8.dp, end = 8.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = favorite.temperature?.let {
                    "${it.toInt()}°"
                } ?: "Unknown",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}


// function to get the weather icon based on the weather condition
fun getWeatherIcon(weatherCondition: String?): Int {
    return when (weatherCondition) {
        "Sunny" -> R.drawable.ic_sunny
        "Cloudy" -> R.drawable.ic_cloudy
        "Rainy" -> R.drawable.ic_rainy
        "Snow" -> R.drawable.ic_snow
        "Thunderstorm" -> R.drawable.ic_thunder
        "Storm" -> R.drawable.ic_storm
        "Foggy" -> R.drawable.ic_foggy
        else -> R.drawable.ic_unknown
    }
}


fun mapWeatherCondition(description: String): String {
    return when (description.toLowerCase()) {
        //clear weather
        "clear sky", "sky is clear" -> "Sunny"
        //cloudy weather
        "few clouds", "scattered clouds", "broken clouds", "overcast clouds" -> "Cloudy"
        //rainy weather
        "light rain", "moderate rain", "heavy intensity rain", "very heavy rain", "extreme rain", "rain" -> "Rainy"
        //snowy weather
        "snow", "light snow", "rain and snow", "heavy snow", "sleet", -> "Snow"
        // thunderstorm weather
        "thunderstorm", "thunderstorm with light rain", "thunderstorm with rain", "thunderstorm with heavy rain" -> "Thunderstorm"
        // Stormy weather
        "storm", "tropical storm", "severe storm" -> "Storm"
        // Foggy weather
        "mist", "smoke", "haze", "fog" -> "Foggy"
        // default weather
        else -> "Unknown"
    }
}