package com.pritamworld.androidweatherapp.domain.usecase


import com.pritamworld.androidweatherapp.domain.model.Weather
import com.pritamworld.androidweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        city: String
    ): Result<Weather> {

        val cleanCity = city.trim()

        if (cleanCity.isBlank()) {

            return Result.failure(
                IllegalArgumentException(
                    "Please enter a city"
                )
            )
        }

        if (cleanCity.length > 100) {

            return Result.failure(
                IllegalArgumentException(
                    "City name is too long"
                )
            )
        }

        return repository.searchWeather(
            cleanCity
        )
    }
}