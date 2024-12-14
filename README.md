# WeatherMate App

<a name="top"></a>

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [How to Build From Source](#how-to-build-from-source)
  - [Prerequisites](#prerequisites)
  - [Setup Instructions](#setup-instructions)
- [Project Structure](#project-structure)
  - [Core Components](#core-components)
  - [Network](#network)
  - [Model](#model)
- [Tech Stack used](#tech-stack-used)
  - [Dependencies](#dependencies)
  - [Plugins](#plugins)
- [How It Works](#how-it-works)
- [Future Improvements](#future-improvements)
- [License](#license)

## Overview
WeatherMate is a Kotlin-based Android application that provides real-time weather data and 7-day forecasts using the OpenWeatherMap API. Designed with Jetpack Compose, the app adheres to modern Android development practices, including dependency injection with Dagger Hilt, to ensure scalability and maintainability.

[↑ Back to top](#top)

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

[↑ Back to top](#top)

## Screenshots

| Splash Screen | Home Screen | 7-Day Forecast | Drop Down Menu |
|---------------|-------------|----------------|----------------|
| ![Splash Screen](assets/screenshots/splash_screen.png) | ![Home Screen](assets/screenshots/home_screen.png) | ![7-Day Forecast](assets/screenshots/7day_forecast_screen.png) | ![Drop Down Menu](assets/screenshots/drop_down_menu.png) |

| Favorites City Screen | Feedback Screen | Setting Screen |
|-----------------------|-----------------|----------------|
| ![Favorites City Screen](assets/screenshots/favorites_city_screen.png) | ![Feedback Screen](assets/screenshots/feedback_screen.png) | ![Setting Screen](assets/screenshots/setting_screen.png) |

[↑ Back to top](#top)

## How to Build From Source

## Prerequisites

- **Android Studio Arctic Fox or later**: Download from [Android Studio](https://developer.android.com/studio).
- **Kotlin**: Ensure Kotlin is configured in your Android Studio.
- **OpenWeatherMap API Key**: Sign up for a free API key at [OpenWeatherMap](https://openweathermap.org/).

[↑ Back to top](#top)

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/kiranpok/WeatherMate.git
