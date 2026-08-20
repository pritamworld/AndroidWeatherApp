package com.pritamworld.androidweatherapp.data.repositoryimpl


import com.pritamworld.androidweatherapp.BuildConfig
import com.pritamworld.androidweatherapp.data.local.PreferencesDataSource
import com.pritamworld.androidweatherapp.data.mapper.toDomain
import com.pritamworld.androidweatherapp.data.remote.GeocodingApi
import com.pritamworld.androidweatherapp.data.remote.WeatherApi
import com.pritamworld.androidweatherapp.domain.model.Weather
import com.pritamworld.androidweatherapp.domain.repository.WeatherRepository
import com.pritamworld.androidweatherapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geocodingApi: GeocodingApi,
    private val preferencesDataSource: PreferencesDataSource
) : WeatherRepository {
    override suspend fun searchWeather(
        city: String
    ): Result<Weather> = withContext(Dispatchers.IO) {
        try {

            val cleanCity = city.trim()

            if (cleanCity.isBlank()) {

                return@withContext Result.failure(
                    IllegalArgumentException(
                        "Please enter a city"
                    )
                )
            }

            if (BuildConfig.OPEN_WEATHER_API_KEY.isBlank()) {

                return@withContext Result.failure(
                    IllegalStateException(
                        "OpenWeather API key is not configured"
                    )
                )
            }

            // Geo encoding for city name
            val locations =
                geocodingApi.getCoordinates(
                    city = "$cleanCity,${Constants.DEFAULT_COUNTRY_CODE}",
                    limit = 5,
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                )

            val location =
                locations.firstOrNull()
                    ?: return@withContext Result.failure(
                        CityNotFoundException()
                    )

            // Get weather details
            val weather =
                weatherApi.getWeatherByCoordinates(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY,
                    units = Constants.UNITS
                )

            val result = weather.toDomain()

            // Save to local storage
            if (result.isSuccess) {
                saveLastSearchedCity(weather.name ?: cleanCity)
            }

            result

        } catch (exception: Exception) {
            Result.failure(mapException(exception))
        }
    }

    override suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<Weather> =
        withContext(Dispatchers.IO) {

            try {

                if (BuildConfig.OPEN_WEATHER_API_KEY.isBlank()) {

                    return@withContext Result.failure(
                        IllegalStateException(
                            "OpenWeather API key is not configured"
                        )
                    )
                }

                val response =
                    weatherApi.getWeatherByCoordinates(
                        latitude = latitude,
                        longitude = longitude,
                        apiKey =
                            BuildConfig.OPEN_WEATHER_API_KEY,
                        units = Constants.UNITS
                    )

                response.toDomain()

            } catch (exception: Exception) {

                Result.failure(
                    mapException(exception)
                )
            }
        }

    override suspend fun getLastSearchedCity(): String? {

        return preferencesDataSource.getLastCity()
    }

    override suspend fun saveLastSearchedCity(
        city: String
    ) {

        preferencesDataSource.saveLastCity(city)
    }

    private fun mapException(
        exception: Exception
    ): Exception {

        return when (exception) {

            is IOException ->
                NetworkException()

            is HttpException -> {

                when (exception.code()) {

                    401 ->
                        UnauthorizedException()

                    404 ->
                        CityNotFoundException()

                    429 ->
                        RateLimitException()

                    else ->
                        ServerException()
                }
            }

            else ->
                exception
        }
    }
}

class CityNotFoundException :
    Exception("City not found")

class NetworkException :
    Exception("Please check your internet connection")

class UnauthorizedException :
    Exception("Weather API authorization failed")

class RateLimitException :
    Exception(
        "Too many requests. Please try again later"
    )

class ServerException :
    Exception(
        "Weather service is temporarily unavailable"
    )