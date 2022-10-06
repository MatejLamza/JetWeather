package com.example.jetweatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherapp.R
import com.example.jetweatherapp.common.state.State
import com.example.jetweatherapp.data.model.Location
import com.example.jetweatherapp.data.repo.WeatherRepository
import com.example.jetweatherapp.utils.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    /**
     * There is no city data to be rendered.
     * This could either be because it's still loading or API haven't returned anything for users
     * request.
     */
    data class CityNotFound(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState

    data class CityFound(
        val weather: Location,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState
}

/**
 * Internal representation of Home route state
 */
private data class HomeViewModelState(
    val weather: Location? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {
    /**
     * Converts this [HomeViewModelState] into a more strongly typed [HomeUiState] for driving the UI.
     *
     */
    fun toUIState(): HomeUiState =
        if (weather == null)
            HomeUiState.CityNotFound(isLoading = isLoading, errorMessages = errorMessages)
        else HomeUiState.CityFound(
            weather = weather,
            isLoading = isLoading,
            errorMessages = errorMessages
        )
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))
    val uiState = viewModelState
        .map { it.toUIState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUIState()
        )

    init {
        viewModelScope.launch {
            val result = repository.getWeather("Zagreb")
            viewModelState.update {
                when (result) {
                    is State.Done -> {
                        it.copy(weather = result.data, isLoading = false)
                    }
                    is State.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                    else -> throw IllegalStateException("")
                }
            }
        }
    }

    fun fetchTemperature(cityName: String) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.getWeather(cityName)
            viewModelState.update {
                when (result) {
                    is State.Done -> it.copy(weather = result.data, isLoading = false)
                    is State.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(weather = null, errorMessages = errorMessages, isLoading = false)
                    }
                    else -> throw IllegalStateException("")
                }
            }
        }
    }

    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val result = repository.getWeather("Zagreb")
            viewModelState.update {
                when (result) {
                    is State.Done -> {
                        it.copy(weather = result.data, isLoading = false)
                    }
                    is State.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                    else -> throw IllegalStateException("")
                }
            }
        }
    }
}
