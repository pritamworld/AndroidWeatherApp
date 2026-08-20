package com.pritamworld.androidweatherapp

import com.pritamworld.androidweatherapp.domain.model.Weather
import com.pritamworld.androidweatherapp.domain.repository.WeatherRepository
import com.pritamworld.androidweatherapp.domain.usecase.SearchWeatherUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchWeatherUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: SearchWeatherUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = SearchWeatherUseCase(repository)
    }

    @Test
    fun `empty city returns failure`() =
        runTest {

            val result = useCase("")

            assertTrue(
                result.isFailure
            )

            assertEquals(
                "Please enter a city",
                result.exceptionOrNull()
                    ?.message
            )
        }

    @Test
    fun `valid city returns weather`() =
        runTest {

            val weather =
                Weather(
                    city = "Detroit",
                    country = "US",
                    temperature = 75.0,
                    feelsLike = 73.0,
                    minTemperature = 68.0,
                    maxTemperature = 79.0,
                    humidity = 55,
                    pressure = 1015,
                    windSpeed = 8.0,
                    description = "Clear sky",
                    iconCode = "01d"
                )

            whenever(
                repository.searchWeather(
                    "Detroit"
                )
            ).thenReturn(
                Result.success(weather)
            )

            val result = useCase("Detroit")

            assertTrue(
                result.isSuccess
            )

            assertEquals(
                "Detroit",
                result.getOrThrow().city
            )
        }
}