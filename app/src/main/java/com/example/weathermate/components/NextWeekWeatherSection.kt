package com.example.weathermate.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.ActivityMappingUtils
import com.example.weathermate.utils.formatDay
import com.example.weathermate.utils.formatDecimals

@Composable
fun NextWeekWeatherSection(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    val (activitySuggestion, activityIconRes) = ActivityMappingUtils.mapWeatherToActivity(weather.weather[0].description, weather.temp.max)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formatDay(weather.dt),
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
        WeatherStateImage(imageUrl = imageUrl)
        Text(
            text = weather.weather[0].description,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(formatDecimals(weather.temp.max))
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                    append(" ${formatDecimals(weather.temp.min)}")
                }
            },
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = activityIconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = activitySuggestion,
                style = MaterialTheme.typography.body2,
                color = Color.White
            )
        }
    }

    Divider(
        color = Color(0xFF3A83D6), thickness = 1.dp,
    )
}