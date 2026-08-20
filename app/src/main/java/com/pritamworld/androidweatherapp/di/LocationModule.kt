package com.pritamworld.androidweatherapp.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.pritamworld.androidweatherapp.location.FusedLocationProvider
import com.pritamworld.androidweatherapp.location.LocationProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        implementation: FusedLocationProvider
    ): LocationProvider

    companion object {
        @Provides
        @Singleton
        fun provideFusedLocationClient(
            @ApplicationContext context: Context
        ) = LocationServices.getFusedLocationProviderClient(context)
    }
}