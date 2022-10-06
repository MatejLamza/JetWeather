package com.example.jetweatherapp.utils.extenesions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jetweatherapp.common.state.State
import kotlinx.coroutines.CoroutineExceptionHandler

fun exceptionHandler(onError: ((Throwable) -> Unit)) =
    CoroutineExceptionHandler { _, exception ->
        Log.e("ViewModel", "Error in ViewModel", exception)
        onError(exception)
    }

fun <T : Any> exceptionHandler(data: MutableLiveData<State<T>>? = null) =
    exceptionHandler {
        data?.postValue(State.Error(it))
    }


