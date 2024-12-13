package com.example.weathermate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.formatDecimals
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TodayWeatherSection(hourlyWeatherList: List<WeatherItem>) {
    val currentTime = LocalDateTime.now()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = hourlyWeatherList) { index, item ->
            val itemTime = currentTime.plusHours(index.toLong())
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(Color(0xFF003366), shape = RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = itemTime.format(timeFormatter),
                    style = MaterialTheme.typography.caption,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                WeatherStateImage(imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png")
                Text(
                    text = formatDecimals(item.temp.day),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
        }
    }
}