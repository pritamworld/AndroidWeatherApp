package com.pritamworld.androidweatherapp.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.pritamworld.androidweatherapp.ui.weather.event.WeatherUiEvent
import com.pritamworld.androidweatherapp.ui.weather.vm.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
) {

    val state by viewModel.uiState.collectAsState()

    // Get current context
    val context = LocalContext.current

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    //Ask for location permission
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] == true ||
                        permissions[
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ] == true

            if (granted) {

                viewModel.onEvent(
                    WeatherUiEvent.CurrentLocation
                )
            } else {

                viewModel.initialize()
            }
        }

    LaunchedEffect(Unit) {

        val coarseGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val fineGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (coarseGranted || fineGranted) {
            viewModel.onEvent(WeatherUiEvent.CurrentLocation)
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Weather",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Display search bar on top
        SearchBar(
            value = searchText,

            onValueChange = {
                searchText = it
            },

            // Search icon click
            onSearch = {

                if (searchText.isNotBlank()) {

                    viewModel.onEvent(
                        WeatherUiEvent.Search(
                            searchText
                        )
                    )
                }
            },

            // Current location icon click
            onLocationClick = {

                val coarseGranted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                val fineGranted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                if (coarseGranted || fineGranted) {
                    viewModel.onEvent(
                        WeatherUiEvent.CurrentLocation
                    )

                } else {

                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                }
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Display weather details
        WeatherContent(
            state = state,

            onRetry = {
                viewModel.onEvent(
                    WeatherUiEvent.Retry
                )
            }
        )
    }
}


