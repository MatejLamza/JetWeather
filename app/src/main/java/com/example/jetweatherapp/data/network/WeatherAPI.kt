package com.example.jetweatherapp.data.network

import com.example.jetweatherapp.data.model.Coordinates
import com.example.jetweatherapp.data.model.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather")
    suspend fun getWeatherForCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Location

    @GET("/geo/1.0/direct")
    suspend fun getLocationCoordinatesByName(
        @Query("q") city: String
    ): List<Coordinates>
}
