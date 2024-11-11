package com.example.weathermate.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weathermate.screens.main.HomeScreen
import com.example.weathermate.screens.main.MainViewModel
import com.example.weathermate.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(
            route = "${WeatherScreens.HomeScreen.name}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: "defaultCity"
            val mainViewModel = hiltViewModel<MainViewModel>()
            HomeScreen(navController = navController, mainViewModel = mainViewModel, city = city)
        }
        composable(WeatherScreens.SearchScreen.name) {
           SearchScreen(navController = navController)
        }
    }
}