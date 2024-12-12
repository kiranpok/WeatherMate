package com.example.weathermate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80,
    background = Color(0xFF121212) // Custom dark background color
)

private val LightColorScheme = lightColors(
    primary = Blue,
    secondary = PurpleGrey40,
    background = Color( 0xFF0057A0) // Custom light background color
)

@Composable
fun WeatherMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // Make system bars transparent
            darkIcons = !darkTheme // Use dark icons for light theme and vice versa
        )
        systemUiController.setNavigationBarColor(
            color = Color(0xFF0057A0) // Set your desired navigation bar color
        )
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}

