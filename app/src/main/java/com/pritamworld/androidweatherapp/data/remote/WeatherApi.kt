package com.pritamworld.androidweatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&units=imperial&appid=<YOUR API KEY>
    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(

        @Query("lat")
        latitude: Double,

        @Query("lon")
        longitude: Double,

        @Query("appid")
        apiKey: String,

        @Query("units")
        units: String
    ): WeatherResponse
}