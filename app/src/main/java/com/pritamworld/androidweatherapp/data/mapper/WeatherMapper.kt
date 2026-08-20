package com.pritamworld.androidweatherapp.data.mapper

import com.pritamworld.androidweatherapp.data.remote.WeatherResponse
import com.pritamworld.androidweatherapp.domain.model.Weather


fun WeatherResponse.toDomain(): Result<Weather> {

    val mainData = main
        ?: return Result.failure(
            IllegalStateException(
                "Weather information is unavailable"
            )
        )

    val weatherData = weather
        ?.firstOrNull()
        ?: return Result.failure(
            IllegalStateException(
                "Weather condition is unavailable"
            )
        )

    val cityName = name
        ?.takeIf { it.isNotBlank() }
        ?: "Unknown"

    return Result.success(
        Weather(
            city = cityName,
            country = sys?.country.orEmpty(),
            temperature = mainData.temp ?: 0.0,
            feelsLike = mainData.feelsLike ?: 0.0,
            minTemperature = mainData.tempMin ?: 0.0,
            maxTemperature = mainData.tempMax ?: 0.0,
            humidity = mainData.humidity ?: 0,
            pressure = mainData.pressure ?: 0,
            windSpeed = wind?.speed ?: 0.0,
            description =
                weatherData.description
                    ?.replaceFirstChar {
                        it.uppercase()
                    }
                    .orEmpty(),

            iconCode = weatherData.icon.orEmpty()
        )
    )
}