# WeatherMate App

WeatherMate is a Kotlin-based Android application that provides current weather data and a 7-day weather forecast using real-time location. It is designed with Jetpack Compose and utilizes modern Android development practices, including dependency injection with Dagger Hilt.

## Features

- **Real-time Weather**: Fetches current weather data for the user's location.
- **7-Day Forecast**: Displays a detailed weather forecast for the next 7 days.
- **Refresh Location**: Allows users to refresh their location and fetch updated weather data.
- **User-Friendly UI**: Designed using Jetpack Compose with Material 3 styling.
- **Dependency Injection**: Powered by Dagger Hilt for better modularity and testability.

---

## Screenshots

### Home Screen
TB Updated

### 7-Day Forecast
TB Updated

---

## Prerequisites

Before running this application, ensure you have the following:

1. Android Studio Arctic Fox or later.
2. API key from [OpenWeatherMap](https://openweathermap.org/).
3. A connected emulator or physical device.

---

## Setup Instructions

1. Clone this repository:
   ```bash
   git clone https://github.com/kiranpok/WeatherMate.git
   cd weather-mate
   ```

2. Open the project in Android Studio.

3. Add your OpenWeatherMap API key:
   - Navigate to `app/src/main/java/com/example/weatherapp/BuildConfig.java` (or its Kotlin equivalent).
   - Replace `"YOUR_API_KEY_HERE"` with your OpenWeatherMap API key.

4. Sync Gradle and build the project.

---

## File Structure

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

1. **Fetch Current Location**:
   - The app uses `LocationUtils` to fetch the device's current location.
   - Requires `ACCESS_FINE_LOCATION` permission.

2. **Fetch Weather Data**:
   - Weather data is fetched using the `WeatherRepository`, which calls the OpenWeatherMap API via `WeatherApiService`.

3. **UI Rendering**:
   - Weather data is displayed on the `WeatherScreen` using Jetpack Compose.
   - The user can navigate to the 7-day forecast via a button.

---

## Future Improvements

- Add more detailed weather insights (e.g., UV index, sunrise/sunset times).
- Implement error handling for network and location issues.
- Add unit tests and UI tests.
- Support for multiple languages and units.

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.

---

