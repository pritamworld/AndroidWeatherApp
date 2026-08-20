package com.pritamworld.androidweatherapp.domain.repository

import com.pritamworld.androidweatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun searchWeather(
        city: String
    ): Result<Weather>

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<Weather>

    suspend fun getLastSearchedCity(): String?

    suspend fun saveLastSearchedCity(
        city: String
    )
}