package com.example.weathermate.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weathermate.R

@Composable
fun WeatherBackground(weatherCondition: String) {
    // Map weather conditions to Lottie animation resources
    val animationResource = when (weatherCondition.lowercase()) {
        "rain" -> R.raw.lottie_rain
        "snow" -> R.raw.lottie_snow
        "thunderstorm" -> R.raw.lottie_thunder
        "broken clouds", "cloudy" -> R.raw.lottie_cloudy
        "clear", "sunny" -> R.raw.lottie_sunny
        else -> R.raw.lottie_sunny // Default to sunny for unrecognized conditions
    }

    // Load the Lottie composition
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResource))

    // Display the animation with looping
    LottieAnimation(
        composition = composition,
        modifier = Modifier.fillMaxSize(),
        iterations = Int.MAX_VALUE // Loop indefinitely
    )
}