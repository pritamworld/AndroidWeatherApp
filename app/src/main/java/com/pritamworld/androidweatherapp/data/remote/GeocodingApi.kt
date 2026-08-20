package com.pritamworld.androidweatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    // http://api.openweathermap.org/geo/1.0/direct?q=new%20york&limit=2&appid=<YOUR API KEY>
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