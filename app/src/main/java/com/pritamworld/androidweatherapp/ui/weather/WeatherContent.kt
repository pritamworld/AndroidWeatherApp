package com.pritamworld.androidweatherapp.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pritamworld.androidweatherapp.ui.weather.state.WeatherUiState

@Composable
fun WeatherContent(
    state: WeatherUiState,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when (state) {

            WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }

            WeatherUiState.Empty -> {
                EmptyWeather()
            }

            is WeatherUiState.Success -> {
                WeatherDetails(
                    weather = state.weather
                )
            }

            is WeatherUiState.Error -> {
                ErrorContent(
                    message = state.message,
                    onRetry = onRetry
                )
            }
        }
    }
}