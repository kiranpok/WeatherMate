package com.example.weathermate.screens.main

import HumidityWindPressureRow
import SunsetSunriseRow
import WeatherStateImage
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weathermate.R
import com.example.weathermate.data.DataOrException
import com.example.weathermate.model.Weather
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.navigation.WeatherScreens
import com.example.weathermate.utils.formatDate
import com.example.weathermate.utils.formatDecimals
import com.example.weathermate.widgets.WeatherMateAppBar


// Home Screen
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city ?: "defaultCity", units = "metric")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherMateAppBar(
            title = weather.city.name + " ,${weather.city.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },

            elevation = 5.dp
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }

    }) { paddingValues ->
        MainContent(
            data = weather,
            modifier = Modifier.padding(paddingValues)

        )
    }
}

@Composable
fun MainContent(data: Weather, modifier: Modifier) {
    val weatherItem = data.list[0]
    val backgroundColor = Color(0xFF4C9EF1)
    val textColor = Color.White
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Background Image
        /**Image(
        painter = painterResource(id = R.drawable.ict_background_image),
        contentDescription = "background image",
        modifier = Modifier.fillMaxSize()
        )**/
        Column(
            Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .size(393.dp, 370.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF3F51B5),
                elevation = 5.dp,
                border = BorderStroke(1.dp, Color(0xFF3A83D6))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = formatDate(data.list[0].dt),
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(6.dp)
                    )

                    // Weather Image
                    WeatherStateImage(imageUrl = imageUrl)
                    Text(
                        text = formatDecimals(data.list[0].temp.day),
                        style = typography.h3,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = data.list[0].weather[0].main,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                        style = MaterialTheme.typography.caption
                    )

                    HumidityWindPressureRow(weather = weatherItem)
                    Divider(
                        color = Color(0xFF3A83D6), thickness = 1.dp,
                    )
                    SunsetSunriseRow(weather = data.list[0])

                }

            }

        }
    }
}
