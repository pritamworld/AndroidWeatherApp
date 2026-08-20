package com.pritamworld.androidweatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("name")
    val name: String?,

    @SerializedName("main")
    val main: MainDto?,

    @SerializedName("weather")
    val weather: List<WeatherConditionDto>?,

    @SerializedName("wind")
    val wind: WindDto?,

    @SerializedName("sys")
    val sys: SysDto?
)

data class MainDto(

    @SerializedName("temp")
    val temp: Double?,

    @SerializedName("feels_like")
    val feelsLike: Double?,

    @SerializedName("temp_min")
    val tempMin: Double?,

    @SerializedName("temp_max")
    val tempMax: Double?,

    @SerializedName("pressure")
    val pressure: Int?,

    @SerializedName("humidity")
    val humidity: Int?
)

data class WeatherConditionDto(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("main")
    val main: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("icon")
    val icon: String?
)

data class WindDto(

    @SerializedName("speed")
    val speed: Double?
)

data class SysDto(

    @SerializedName("country")
    val country: String?
)