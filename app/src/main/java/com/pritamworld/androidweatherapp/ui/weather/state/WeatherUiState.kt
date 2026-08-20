package com.pritamworld.androidweatherapp.ui.weather.state


import com.pritamworld.androidweatherapp.domain.model.Weather

sealed interface WeatherUiState {

    data object Loading : WeatherUiState

    data object Empty : WeatherUiState

    data class Success(
        val weather: Weather
    ) : WeatherUiState

    data class Error(
        val message: String
    ) : WeatherUiState
}