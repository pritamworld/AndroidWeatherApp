package com.pritamworld.androidweatherapp.domain.usecase


import com.pritamworld.androidweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLastSearchedCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(): String? {
        return repository.getLastSearchedCity()
    }
}