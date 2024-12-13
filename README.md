# WeatherMate App

## Overview
WeatherMate is a Kotlin-based Android application that provides real-time weather data and 7-day forecasts using the OpenWeatherMap API. Designed with Jetpack Compose, the app adheres to modern Android development practices, including dependency injection with Dagger Hilt, to ensure scalability and maintainability.

## Features
- **Real-Time Weather**: Fetches current weather data for the user's location.
- **7-Day and Hourly Forecasts**: Displays detailed weather information for the next 7 days and hourly updates.
- **Refresh Location**: Allows users to update weather data by refreshing their location.
- **Saved Locations**: Enables users to save and manage their favorite cities.
- **Unit Preferences**: Switch between Celsius and Fahrenheit.
- **Weather Alerts**: Sends notifications for significant weather changes.
- **Activity Recommendations**: Provides personalized suggestions based on weather conditions.
- **User-Friendly UI**: Designed using Jetpack Compose with Material 3 styling.
- **Accessibility**: High-contrast design and clear labels for inclusivity.

---

## Screenshots

### Home Screen
![Home Screen](screenshots/home_screen.png)

### 7-Day Forecast
![7-Day Forecast](screenshots/7day_forecast_screen.png)

### Drop Down Menu
![Drop Down Menu](screenshots/drop_downn_menu.png)

### Favorites City Screen
![Favorites City Screen](screenshots/favorites_city_screen.png)

### Feedback Screen
![Feedback Screen](screenshots/feedback_screen.png)

### Setting Screen
![Setting Screen](screenshots/setting_screen.png)

### Splash Screen
![Splash Screen](screenshots/splash_screen.png)

---
## How to Build From Source

## Prerequisites

- **Android Studio Arctic Fox or later**: Download from [Android Studio](https://developer.android.com/studio).
- **Kotlin**: Ensure Kotlin is configured in your Android Studio.
- **OpenWeatherMap API Key**: Sign up for a free API key at [OpenWeatherMap](https://openweathermap.org/).


---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/kiranpok/WeatherMate.git
   ```
   ```bash
   cd WeatherMate
   ```
2. Open the project in Android Studio.
3. Add your API key to the `local.properties` file:
   ```
   WEATHER_API_KEY=your_api_key_here
   ```
4. Build the project by selecting **Build > Make Project** in Android Studio.
5. Run the app on an emulator or connected device.

---

## File Structure
weathermate/
├── MainActivity.kt                // Entry point for the app
├── navigation/
│   └── WeatherNavigation.kt       // Handles app navigation
├── screens/
│   ├── favorites/
│   │   └── FavoriteCityViewModel.kt  // ViewModel for managing favorite cities
│   ├── main/
│   │   └── MainViewModel.kt          // ViewModel for main screen
│   ├── seven_day_forecast/
│   │   └── SevenDayForecastScreen.kt // Composable for 7-day forecast screen
│   ├── drop_downn_menu/
│   │   └── DropDownMenuScreen.kt     // Composable for drop-down menu screen
│   ├── favorites_city_screen/
│   │   └── FavoritesCityScreen.kt    // Composable for favorite city screen
│   ├── feedback_screen/
│   │   └── FeedbackScreen.kt         // Composable for feedback screen
│   ├── home_screen/
│   │   └── HomeScreen.kt             // Composable for home screen
│   ├── setting_screen/
│   │   └── SettingScreen.kt          // Composable for settings screen
│   └── splash_screen/
│       └── SplashScreen.kt           // Composable for splash screen
├── ui/
│   └── theme/
│       └── WeatherMateTheme.kt       // Theme definitions for the app
├── utils/
│   └── LocationUtils.kt              // Utility functions for location services
├── di/
│   └── AppModule.kt                  // Dagger Hilt module for dependency injection
├── network/
│   ├── WeatherApiService.kt          // Retrofit service interface for weather API
│   └── RetrofitClient.kt             // Singleton for initializing Retrofit
├── model/
│   ├── Weather.kt                    // Data model for weather data
│   ├── City.kt                       // Data model for city information
│   └── WeatherItem.kt                // Data model for a single weather forecast entry
└── repository/
    └── WeatherRepository.kt          // Repository for handling API calls
### Core Components
- **MainActivity.kt**: Entry point for the app. Handles navigation using Jetpack Navigation.
- **WeatherScreen.kt**: Displays current weather data and navigation options.
- **SevenDayForecast.kt**: Lists the 7-day weather forecast using a lazy column.
- **WeatherViewModel.kt**: ViewModel for managing weather data and state.
- **WeatherRepository.kt**: Repository for handling API calls.
- **AppModule.kt**: Dagger Hilt module for dependency injection.
- **LocationUtils.kt**: Handles location fetching using FusedLocationProviderClient.

### Network
- **WeatherApiService.kt**: Retrofit service interface for the OpenWeatherMap API.
- **RetrofitClient.kt**: Singleton for initializing the Retrofit instance.

### Model
- **Weather.kt**: Data model representing weather data.
- **City.kt**: Represents city data.
- **WeatherItem.kt**: Represents a single weather forecast entry.

---

## Dependencies

- **Jetpack Compose**: For building declarative UI.
- **Retrofit**: For API requests.
- **Dagger Hilt**: For dependency injection.
- **Google Play Services**: For location services.

---

## How It Works

- **Current Location Weather**: Automatically fetches and displays weather data based on the user's real-time GPS location.
- **Search City Weather**: Users can search for weather data of any city globally.
- **Save Cities**: Users can save their favorite cities for quick access to weather information.
- **Unit Changes**: The app allows switching between Celsius and Fahrenheit, and updates all displayed temperatures accordingly.
- **Activity Recommendations**: The app provides personalized activity suggestions based on the weather conditions.
- **Weather Alerts**: Notifies users about significant weather changes, like storms or extreme temperatures.
- **Feedback**: Users can submit feedback directly through the app for continuous improvement.

---

## Future Improvements

- Expand the activity recommendation engine with AI-driven insights.
- Incorporate more detailed weather parameters, such as air quality and UV index.
- Launch on multiple platforms, including iOS.
- Support for multiple languages.

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.

---

