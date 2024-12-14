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

| Splash Screen | Home Screen | 7-Day Forecast | Drop Down Menu |
|---------------|-------------|----------------|----------------|
| ![Splash Screen](assets/screenshots/splash_screen.png) | ![Home Screen](assets/screenshots/home_screen.png) | ![7-Day Forecast](assets/screenshots/7day_forecast_screen.png) | ![Drop Down Menu](assets/screenshots/drop_down_menu.png) |

| Favorites City Screen | Feedback Screen | Setting Screen |
|-----------------------|-----------------|----------------|
| ![Favorites City Screen](assets/screenshots/favorites_city_screen.png) | ![Feedback Screen](assets/screenshots/feedback_screen.png) | ![Setting Screen](assets/screenshots/setting_screen.png) |

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



### Project Structure
![Project Structure](assets/screenshots/project_structure.png)
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

### Tech Stack used
## Dependencies

| **Tech Tools**                                   | **Usage/Purpose**                                                                                     |
|-------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| [**Kotlin**](https://kotlinlang.org/)           | Primary programming language used for building the app                                               |
| [**Jetpack Compose**](https://developer.android.com/jetpack/compose) | Framework for declarative UI creation and building responsive layouts                                |
| [**Compose Navigation**](https://developer.android.com/jetpack/compose/navigation) | Library for navigating between screens within the Compose framework                                  |
| [**Coil**](https://coil-kt.github.io/coil/)     | Image loading library optimized for Android                                                         |
| [**Coroutines**](https://developer.android.com/kotlin/coroutines) | Enable asynchronous programming for tasks like API calls, database access, and background processing |
| [**Hilt (Dependency Injection)**](https://dagger.dev/hilt/) | Framework for managing dependencies and achieving modularization                                    |
| [**Retrofit**](https://square.github.io/retrofit/) | HTTP client library used for making API calls to fetch weather and alert data                       |
| [**OkHTTP**](https://square.github.io/okhttp/)  | Networking library required by Retrofit for HTTP requests                                           |
| [**Room DB**](https://developer.android.com/training/data-storage/room) | Local database solution to store weather data, user preferences, and cached results                 |
| [**Kotlinx Serialization**](https://github.com/Kotlin/kotlinx.serialization) | Serialization library used for converting Kotlin objects to/from JSON                               |
| [**AndroidX**](https://developer.android.com/jetpack/androidx) | A collection of modern libraries for Android development (ViewModel, LiveData, etc.)               |
| [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel) | Architecture component for managing UI-related data in a lifecycle-aware manner                     |
| [**android-maps-compose**](https://googlemaps.github.io/android-maps-compose/) | Compose library for integrating Google Maps to display user locations and weather                   |
| [**Gson**](https://github.com/google/gson)      | JSON parsing library used for handling API responses (alternative to kotlinx.serialization)         |
| [**OpenWeatherMap**](https://openweathermap.org/) | API used to fetch real-time weather and forecast data                                               |
| [**Accompanist System UI Controller**](https://google.github.io/accompanist/systemuicontroller/) | Manage status bar and navigation bar appearance                                                     |
| [**Lottie Animation**](https://airbnb.io/lottie/#/) | Add vector animations to the app                                                                    |
| [**Google Play Services Location**](https://developer.android.com/training/location) | Provides precise user location to deliver localized weather data                                    |
| [**Accompanist Permissions**](https://google.github.io/accompanist/permissions/) | Simplifies permissions handling in Jetpack Compose                                                  |


## Plugins
| **Plugins**                                             | **Usage/Purpose**                                                                                     |
|---------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| [**Android Application Plugin**](https://developer.android.com/studio/build) | Core plugin for building Android applications                                                        |
| [**Kotlin Android Plugin**](https://kotlinlang.org/docs/android.html)        | Enables using Kotlin as the programming language for Android apps                                    |
| [**Kotlin KAPT**](https://kotlinlang.org/docs/kapt.html)                    | Kotlin Annotation Processing Tool for code generation (used by Hilt and Room)                        |
| [**Hilt Android Plugin**](https://dagger.dev/hilt/)                         | Simplifies dependency injection setup in Android projects                                             |

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

