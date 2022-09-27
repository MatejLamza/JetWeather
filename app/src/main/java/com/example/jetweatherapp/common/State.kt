package com.example.jetweatherapp.common

sealed class State

object Done : State()
object Loading : State()
data class Error(val throwable: Throwable? = null) : State()
