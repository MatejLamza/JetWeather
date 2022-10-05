package com.example.jetweatherapp.data.repo

import com.example.jetweatherapp.common.state.State
import com.example.jetweatherapp.data.model.Coordinates
import com.example.jetweatherapp.data.model.Location
import com.example.jetweatherapp.data.network.WeatherAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * @param coroutineDispatcher is Context on which coroutines are being executed.
 * Default is [IO] which is optimized for this type of work.
 * Dispatchers are injected thorugh constructor because they are easy to replace in unit and
 * instrumentation tests with a [TestDispatcher]. (should implement kotlinx-coroutines-test library)
 */

class WeatherRepository(
    private val api: WeatherAPI,
    private val coroutineDispatcher: CoroutineDispatcher = IO
) {

    private fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<Location> {
        return flow {
            emit(api.getWeatherForCoordinates(lat, lon))
        }.flowOn(coroutineDispatcher)
    }

    private fun getCoordinates(cityName: String): Flow<Coordinates> {
        return flow {

            emit(api.getLocationCoordinatesByName(cityName)[0])
        }.flowOn(coroutineDispatcher)
    }

    suspend fun getWeather(cityName: String) = withContext(coroutineDispatcher) {
        return@withContext kotlin.runCatching {
            val cords = getCoordinates(cityName).first()

            val location = getWeatherForCoordinates(cords.lat, cords.lon).first()
            State.Done<Location>(data = location)
        }.getOrDefault(State.Error(IllegalStateException("Location not found")))
    }
}
