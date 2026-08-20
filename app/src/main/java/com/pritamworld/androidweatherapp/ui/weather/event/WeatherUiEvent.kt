package com.pritamworld.androidweatherapp.ui.weather.event

sealed interface WeatherUiEvent {

    data class Search(
        val city: String
    ) : WeatherUiEvent

    data object CurrentLocation :
        WeatherUiEvent

    data object Retry :
        WeatherUiEvent
}