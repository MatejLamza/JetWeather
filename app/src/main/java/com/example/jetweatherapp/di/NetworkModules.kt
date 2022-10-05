package com.example.jetweatherapp.di

import com.example.jetweatherapp.BuildConfig
import com.example.jetweatherapp.data.network.WeatherAPI
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url =
                chain.request().url().newBuilder().addQueryParameter("appid", BuildConfig.API_KEY)
                    .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(provideInterceptor())
            .build()

    @Singleton
    @Provides
    fun provideWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(provideClient())
            .build()
            .create(WeatherAPI::class.java)
    }
}
