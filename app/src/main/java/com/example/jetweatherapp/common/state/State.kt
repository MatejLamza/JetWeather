package com.example.jetweatherapp.common.state

sealed class State<out T> {
    data class Done<out T>(val data: T) : State<T>()
    data class Error(val error: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
}
