package com.pritamworld.androidweatherapp.ui.weather.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pritamworld.androidweatherapp.domain.usecase.GetLastSearchedCityUseCase
import com.pritamworld.androidweatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.pritamworld.androidweatherapp.domain.usecase.SearchWeatherUseCase
import com.pritamworld.androidweatherapp.location.LocationProvider
import com.pritamworld.androidweatherapp.ui.weather.event.WeatherUiEvent
import com.pritamworld.androidweatherapp.ui.weather.state.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val searchWeatherUseCase: SearchWeatherUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)

    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var lastSearch: String? = null

    fun initialize() {

        if (_uiState.value !is WeatherUiState.Loading) {
            return
        }

        viewModelScope.launch {

            // Get last searched city
            val lastCity = getLastSearchedCityUseCase()

            if (!lastCity.isNullOrBlank()) {
                lastSearch = lastCity

                searchWeather(
                    lastCity,
                    showLoading = false
                )

            } else {
                _uiState.value = WeatherUiState.Empty
            }
        }
    }

    fun onEvent(
        event: WeatherUiEvent
    ) {

        when (event) {

            // User entered city in search box
            is WeatherUiEvent.Search -> {
                searchWeather(
                    event.city
                )
            }

            // Get current device location
            WeatherUiEvent.CurrentLocation -> {
                loadCurrentLocation()
            }


            // if not device location ON then use last search city
            WeatherUiEvent.Retry -> {
                lastSearch?.let {
                    searchWeather(it)
                } ?: initialize()
            }
        }
    }

    private fun searchWeather(
        city: String,
        showLoading: Boolean = true
    ) {

        viewModelScope.launch {

            if (showLoading) {
                _uiState.value = WeatherUiState.Loading
            }

            val result = searchWeatherUseCase(city)

            result
                .onSuccess { weather ->

                    lastSearch = weather.city

                    _uiState.value = WeatherUiState.Success(weather)
                }
                .onFailure { exception ->

                    _uiState.value =
                        WeatherUiState.Error(
                            exception.message
                                ?: "Unable to load weather details"
                        )
                }
        }
    }

    // Load current location from device
    private fun loadCurrentLocation() {

        viewModelScope.launch {

            _uiState.value = WeatherUiState.Loading

            val locationResult = locationProvider.getCurrentLocation()

            locationResult
                .onSuccess { location ->
                    val weatherResult =
                        getWeatherByLocationUseCase(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )

                    weatherResult
                        .onSuccess { weather ->
                            lastSearch = weather.city
                            _uiState.value = WeatherUiState.Success(weather)
                        }
                        .onFailure { exception ->
                            _uiState.value =
                                WeatherUiState.Error(exception.message ?: "Unable to load weather details")
                        }
                }
                .onFailure { exception ->
                    _uiState.value =
                        WeatherUiState.Error(exception.message ?: "Unable to determine location details")
                }
        }
    }
}