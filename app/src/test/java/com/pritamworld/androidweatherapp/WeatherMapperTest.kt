package com.pritamworld.androidweatherapp


import com.pritamworld.androidweatherapp.data.mapper.toDomain
import com.pritamworld.androidweatherapp.data.remote.MainDto
import com.pritamworld.androidweatherapp.data.remote.SysDto
import com.pritamworld.androidweatherapp.data.remote.WeatherConditionDto
import com.pritamworld.androidweatherapp.data.remote.WeatherResponse
import com.pritamworld.androidweatherapp.data.remote.WindDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherMapperTest {

    @Test
    fun `weather response maps correctly`() {

        val response =
            WeatherResponse(

                name = "Detroit",

                main =
                    MainDto(
                        temp = 75.0,
                        feelsLike = 73.0,
                        tempMin = 68.0,
                        tempMax = 79.0,
                        pressure = 1015,
                        humidity = 55
                    ),

                weather =
                    listOf(
                        WeatherConditionDto(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    ),

                wind =
                    WindDto(
                        speed = 8.0
                    ),

                sys =
                    SysDto(
                        country = "US"
                    )
            )

        val result = response.toDomain()

        assertTrue(result.isSuccess)

        val weather = result.getOrThrow()

        assertEquals(
            "Detroit",
            weather.city
        )

        assertEquals(
            75.0,
            weather.temperature,
            0.01
        )

        assertEquals(
            "US",
            weather.country
        )
    }
}