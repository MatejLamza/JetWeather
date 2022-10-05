package com.example.jetweatherapp.di

import com.example.jetweatherapp.data.network.WeatherAPI
import com.example.jetweatherapp.data.repo.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules {

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherAPI): WeatherRepository {
        return WeatherRepository(weatherApi)
    }

}
