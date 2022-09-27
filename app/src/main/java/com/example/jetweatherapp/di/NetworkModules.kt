package com.example.jetweatherapp.di

import com.example.jetweatherapp.BuildConfig
import com.example.jetweatherapp.data.network.WeatherAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    @Singleton
    @Provides
    fun provideWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherAPI::class.java)
    }
}
