package com.pritamworld.androidweatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pritamworld.androidweatherapp.ui.weather.WeatherScreen
import com.pritamworld.androidweatherapp.ui.weather.vm.WeatherViewModel

// Routes List
object Routes {
    const val WEATHER = "weather"
}

@Composable
fun WeatherNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WEATHER
    ) {

        composable(
            route = Routes.WEATHER
        ) {
            val viewModel: WeatherViewModel = hiltViewModel()
            WeatherScreen(viewModel = viewModel)
        }
    }
}