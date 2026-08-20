package com.pritamworld.androidweatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("geo/1.0/direct")
    suspend fun getCoordinates(

        @Query("q")
        city: String,

        @Query("limit")
        limit: Int = 5,

        @Query("appid")
        apiKey: String
    ): List<GeocodingResponse>
}