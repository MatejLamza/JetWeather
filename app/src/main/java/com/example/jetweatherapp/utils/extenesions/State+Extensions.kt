package com.example.jetweatherapp.utils.extenesions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherapp.common.state.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun exceptionHandler(onError: ((Throwable) -> Unit)) =
    CoroutineExceptionHandler { _, exception ->
        Log.e("ViewModel", "Error in ViewModel", exception)
        onError(exception)
    }

fun <T : Any> exceptionHandler(data: MutableLiveData<State<T>>? = null) =
    exceptionHandler {
        data?.postValue(State.Error(it))
    }

fun <T : Any> ViewModel.launch(
    context: CoroutineContext = exceptionHandler<T>(null),
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(context, start) {
        block(this)
    }
}

/*fun <T : Any> ViewModel.launchWithState(
    data: MutableLiveData<State<T>>,
    onError: ((Throwable) -> Unit)? = { data.value = State.Error(it) },
    onLoading: () -> Unit = { data.value = State.Loading },
    onDone: (T?) -> Unit = { data.value = State.Done(data = it) },
    context: CoroutineContext =
        if (onError != null) exceptionHandler(onError)
        else exceptionHandler(data),
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    launch<T>(context, start) {
        onLoading.invoke()
        block(this)
        onDone.invoke()
    }
}*/


