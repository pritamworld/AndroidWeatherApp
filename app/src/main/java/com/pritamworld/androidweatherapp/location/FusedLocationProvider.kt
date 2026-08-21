package com.pritamworld.androidweatherapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class FusedLocationProvider @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val fusedLocationClient:
    FusedLocationProviderClient
) : LocationProvider {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<UserLocation> {

        // Get current location service from device
        val locationManager =
            context.getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager

        // Check which location provider is enabled
        val locationEnabled =
            locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            ) ||
                    locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    )

        if (!locationEnabled) {

            return Result.failure(
                IllegalStateException(
                    "Location services are disabled"
                )
            )
        }

        return suspendCancellableCoroutine { continuation ->

            val cancellationTokenSource =
                CancellationTokenSource()

            // Get current location
            fusedLocationClient
                .getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY, // Less device battery usage
                    cancellationTokenSource.token
                )
                .addOnSuccessListener { location ->

                    if (location != null) {

                        continuation.resume(
                            Result.success(
                                UserLocation(
                                    latitude = location.latitude,
                                    longitude = location.longitude
                                )
                            )
                        )

                    } else {

                        continuation.resume(
                            Result.failure(
                                IllegalStateException(
                                    "Unable to determine location"
                                )
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->

                    continuation.resume(
                        Result.failure(exception)
                    )
                }

            continuation.invokeOnCancellation {

                cancellationTokenSource.cancel()
            }
        }
    }
}