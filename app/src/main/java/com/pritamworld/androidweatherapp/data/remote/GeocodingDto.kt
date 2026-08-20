package com.pritamworld.androidweatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(

    @SerializedName("name")
    val name: String?,

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double,

    @SerializedName("country")
    val country: String?,

    @SerializedName("state")
    val state: String?
)