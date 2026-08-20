package com.pritamworld.androidweatherapp.ui.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pritamworld.androidweatherapp.domain.model.Weather

@Composable
fun WeatherStats(
    weather: Weather
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            WeatherRow(
                label = "High",
                value = "${weather.maxTemperature.toInt()}°F"
            )

            WeatherRow(
                label = "Low",
                value = "${weather.minTemperature.toInt()}°F"
            )

            WeatherRow(
                label = "Humidity",
                value = "${weather.humidity}%"
            )

            WeatherRow(
                label = "Wind",
                value = "${weather.windSpeed} mph"
            )

            WeatherRow(
                label = "Pressure",
                value = "${weather.pressure} hPa"
            )
        }
    }
}