# Weather Android App

A native Android weather application built using Kotlin and MVVM Android architecture.

## Features
- Display weather info of last search city if current location not available to permission to access device location is OFF.
- Search weather by US city
- Automatic current location weather
- Location permission handling
- Last searched city persistence
- OpenWeatherMap integration
- Weather icons
- Loading states
- Error handling
- Retry
- Defensive input validation
- Image caching
- Unit tests
- Jetpack Compose UI

## Architecture

The application follows MVVM with a Repository and Use Case layer.

Compose UI -> ViewModel -> Use Cases -> Repository -> Remote / Local Data Sources

![App Architecture](data/architecture.png)

## Technologies

- Kotlin
- Used KSP instead for KAPT
- Jetpack Compose (UI)
- MVVM (Architecture)
- Retrofit (Network call)
- OkHttp (HTTP Client for Retrofit)
- Kotlin Coroutines (Async)
- Flow / StateFlow
- Hilt (Dependency Injection)
- DataStore (Local Data Storage)
- Coil (Image loading)
- Jetpack Navigation
- JUnit (Testing)
- Mockito (Testing)

## OpenWeatherMap API

The application uses:

Current Weather API:

https://api.openweathermap.org/data/2.5/weather

Geocoding API:

https://api.openweathermap.org/geo/1.0/direct

City names are converted to coordinates through the OpenWeather Geocoding API before requesting weather.

## API Key

Create:

```local.properties```

and add:

```OPEN_WEATHER_API_KEY=YOUR_API_KEY```

**NOTE: Do not commit local.properties.**

## Build

Open the project in Android Studio.

Sync Gradle.

Run:

```./gradlew test```

Then:

```./gradlew assembleDebug```

## Architecture Principles

- The UI does not directly communicate with Retrofit.
- The ViewModel does not contain networking code.
- The Repository abstracts data sources.
- DTOs are converted to domain models.
- Location is abstracted behind LocationProvider.
- DataStore is abstracted behind PreferencesDataSource.
- This makes the application easier to test and maintain.

## Screens
![Plano US](data/1.png)
![New York US](data/2.png)
![New York US](data/3.png)

[![Recording](data/1.png)](data/sr1.webm)