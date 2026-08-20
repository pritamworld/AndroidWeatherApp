package com.pritamworld.androidweatherapp.location

data class UserLocation(
    val latitude: Double,
    val longitude: Double
)

interface LocationProvider {

    suspend fun getCurrentLocation(): Result<UserLocation>
}