package com.example.weathermate.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.generateWeatherSummary

@Composable
fun WeatherSummaryCard(weatherItem: WeatherItem, aiInsights: String) {
    val summary = generateWeatherSummary(weatherItem)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        backgroundColor = Color(0xFF4C9EF1),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Weather Summary",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = summary,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "AI Insights",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = aiInsights,
                style = MaterialTheme.typography.body1
            )
        }
    }
}