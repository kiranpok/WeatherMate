package com.example.weathermate.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weathermate.screens.favorites.FavoriteCityScreen
import com.example.weathermate.screens.feedbacks.FeedbacksScreen
import com.example.weathermate.screens.main.HomeScreen
import com.example.weathermate.screens.main.MainViewModel
import com.example.weathermate.screens.settings.SettingsScreen
import com.example.weathermate.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    // Create a NavController to manage navigation within the app
    val navController = rememberNavController()

    // Set up the NavHost with the NavController and start destination
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        // Define the composable for the splash screen
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        // Define the composable for the home screen with a city argument
        composable(
            route = "${WeatherScreens.HomeScreen.name}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: "defaultCity"
            val mainViewModel = hiltViewModel<MainViewModel>()
            HomeScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                city = city
            )
        }

        // Define the composable for the search screen
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        // Define the composable for the favorite city screen
        composable(WeatherScreens.FavoriteCityScreen.name) {
            FavoriteCityScreen(navController = navController)
        }

        // Define the composable for the settings screen
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }

        // Define the composable for the feedbacks screen
        composable(WeatherScreens.FeedbacksScreen.name) {
            FeedbacksScreen(navController = navController)
        }
    }
}