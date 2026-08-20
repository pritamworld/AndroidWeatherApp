package com.pritamworld.androidweatherapp.domain.usecase

import com.pritamworld.androidweatherapp.domain.model.Weather
import com.pritamworld.androidweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<Weather> {

        return repository.getWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude
        )
    }
}