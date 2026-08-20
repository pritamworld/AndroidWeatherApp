package com.pritamworld.androidweatherapp.utils

// see data folder for JSON response attributes
object Constants {
    //https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    const val WEATHER_BASE_URL = "https://api.openweathermap.org/"

    // Change if another service http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit={limit}&appid={API key}
    const val GEOCODING_BASE_URL = "https://api.openweathermap.org/"
    const val DEFAULT_COUNTRY_CODE = "US"
    const val UNITS = "imperial" // miles, fahrenheit
}