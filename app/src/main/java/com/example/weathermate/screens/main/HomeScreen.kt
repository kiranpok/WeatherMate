package com.example.weathermate.screens.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weathermate.components.HumidityWindPressureRow
import com.example.weathermate.components.NextWeekWeatherSection
import com.example.weathermate.components.SunsetSunriseRow
import com.example.weathermate.components.TodayWeatherSection
import com.example.weathermate.components.WeatherStateImage
import com.example.weathermate.data.DataOrException
import com.example.weathermate.model.Weather
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.navigation.WeatherScreens
import com.example.weathermate.screens.settings.SettingsViewModel
import com.example.weathermate.ui.theme.WeatherBackground
import com.example.weathermate.utils.formatDate
import com.example.weathermate.utils.formatDecimals
import com.example.weathermate.components.navbar.WeatherMateAppBar

@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    //Collect the unit from the settings view model
    val unitList by settingsViewModel.unitList.collectAsState()
    //Get the unit from the list(metric or imperial) from the unit and set the default to metric
    val unit = unitList.firstOrNull()?.unit?.split(" ")?.get(0)?.lowercase() ?: "metric"
    //Provide state for weather data
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city.toString(), units = unit)
    }.value
    // Display the loading indicator, weather data, or error message
    when {
        weatherData.loading == true -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        weatherData.data != null -> {
            // Get the weather alerts if data is available
            val weatherAlerts = remember {
                weatherData.data?.let {
                    mainViewModel.getWeatherAlerts(it.list, unit == "metric")
                } ?: listOf()
            }

            MainScaffold(weather = weatherData.data!!, navController, weatherAlerts)
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Unable to load weather data. Please check your network connection.")
            }
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, weatherAlerts: List<String>) {
    Scaffold(
        topBar = {
            WeatherMateAppBar(
                title = "${weather.city.name}, ${weather.city.country}",
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 5.dp
            )
        }
    ) { paddingValues ->
        MainContent(
            data = weather,
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            weatherAlerts = weatherAlerts
        )
    }
}

@Composable
fun MainContent(data: Weather, modifier: Modifier, navController: NavController, weatherAlerts: List<String>) {
    val weatherItem = data.list[0]
    val backgroundColor = Color(0xFF0057A0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Surface(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(393.dp, 350.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF003366),
                    elevation = 5.dp,
                    border = BorderStroke(1.dp, Color(0xFF3F51B5))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        WeatherBackground(weatherCondition = weatherItem.weather[0].main)
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = formatDate(weatherItem.dt),
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(6.dp)
                            )
                            WeatherStateImage(imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png")
                            Text(
                                text = formatDecimals(weatherItem.temp.day),
                                style = MaterialTheme.typography.h2,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = weatherItem.weather[0].description,
                                fontStyle = FontStyle.Italic,
                                color = Color.White,
                                style = MaterialTheme.typography.caption
                            )
                            HumidityWindPressureRow(weather = weatherItem)
                            Divider(color = Color.White, thickness = 1.dp)
                            SunsetSunriseRow(weather = weatherItem)
                        }
                    }
                }
            }

            item {
                if (weatherAlerts.isNotEmpty()) {
                    AlertSection(alerts = weatherAlerts)
                }
            }

            item {
                Text("Today", style = MaterialTheme.typography.subtitle1, color = Color.White)
                TodayWeatherSection(hourlyWeatherList = data.list)
                Spacer(modifier = Modifier.height(8.dp))
                Text("7-Day Forecast", style = MaterialTheme.typography.subtitle1, color = Color.White)
                Divider(color = Color.White, thickness = 0.2.dp)
            }

            items(data.list) { item: WeatherItem ->
                NextWeekWeatherSection(weather = item)
            }
        }
    }
}

@Composable
fun AlertSection(alerts: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        alerts.forEach { alert ->
            Text(
                text = alert,
                style = MaterialTheme.typography.body2.copy(color = Color.White),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }
    }
}