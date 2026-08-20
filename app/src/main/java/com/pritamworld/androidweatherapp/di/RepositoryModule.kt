package com.pritamworld.androidweatherapp.di

import com.pritamworld.androidweatherapp.data.repositoryimpl.WeatherRepositoryImpl
import com.pritamworld.androidweatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        implementation: WeatherRepositoryImpl
    ): WeatherRepository
}