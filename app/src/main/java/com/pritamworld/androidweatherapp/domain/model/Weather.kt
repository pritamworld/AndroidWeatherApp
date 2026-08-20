package com.pritamworld.androidweatherapp.domain.model

data class Weather(
    val city: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val description: String,
    val iconCode: String
) {

    val iconUrl: String
        get() =
            "https://openweathermap.org/img/wn/${iconCode}@2x.png"
}