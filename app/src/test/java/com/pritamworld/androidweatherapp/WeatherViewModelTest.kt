package com.pritamworld.androidweatherapp

import com.pritamworld.androidweatherapp.domain.model.Weather
import com.pritamworld.androidweatherapp.domain.usecase.GetLastSearchedCityUseCase
import com.pritamworld.androidweatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.pritamworld.androidweatherapp.domain.usecase.SearchWeatherUseCase
import com.pritamworld.androidweatherapp.location.LocationProvider
import com.pritamworld.androidweatherapp.ui.weather.event.WeatherUiEvent
import com.pritamworld.androidweatherapp.ui.weather.state.WeatherUiState
import com.pritamworld.androidweatherapp.ui.weather.vm.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var searchUseCase: SearchWeatherUseCase
    private lateinit var locationUseCase: GetWeatherByLocationUseCase
    private lateinit var lastCityUseCase: GetLastSearchedCityUseCase
    private lateinit var locationProvider: LocationProvider
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {

        Dispatchers.setMain(dispatcher)

        searchUseCase = mock()
        locationUseCase = mock()
        lastCityUseCase = mock()
        locationProvider = mock()
        viewModel =
            WeatherViewModel(
                searchWeatherUseCase = searchUseCase,
                getWeatherByLocationUseCase = locationUseCase,
                getLastSearchedCityUseCase = lastCityUseCase,
                locationProvider = locationProvider
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `successful city search updates state`() =
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
                searchUseCase("Detroit")
            ).thenReturn(
                Result.success(weather)
            )

            viewModel.onEvent(
                WeatherUiEvent.Search(
                    "Detroit"
                )
            )

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertEquals(
                WeatherUiState.Success(weather),
                state
            )
        }
}