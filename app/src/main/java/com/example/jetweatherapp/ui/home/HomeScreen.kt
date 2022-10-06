package com.example.jetweatherapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jetweatherapp.R
import com.example.jetweatherapp.common.components.JetWeatherSnackbarHost
import com.example.jetweatherapp.common.components.LoadingContent
import com.example.jetweatherapp.ui.home.components.BottomInfoCard
import com.example.jetweatherapp.ui.home.components.SearchBar
import com.example.jetweatherapp.ui.home.components.TemperatureInformationHeader

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefreshScreen: () -> Unit,
    onErrorDismissed: (Long) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Scaffold(
            snackbarHost = { JetWeatherSnackbarHost(hostState = snackbarHostState) },
            modifier = modifier
        ) { innerPadding ->
            val contentModifier = Modifier
                .padding(innerPadding)
            Image(
                painterResource(R.drawable.background),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                LoadingContent(
                    empty = when (uiState) {
                        is HomeUiState.CityNotFound -> uiState.isLoading
                        is HomeUiState.CityFound -> false
                    },
                    emptyContent = {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            Search(onSearch = onSearch)
                        }
                    },
                    loading = uiState.isLoading,
                    onRefresh = onRefreshScreen,
                    content = {
                        when (uiState) {
                            is HomeUiState.CityNotFound -> {
                                if (uiState.errorMessages.isEmpty()) {
                                    // if there are no posts, and no error, let the user refresh manually
                                    TextButton(
                                        onClick = onRefreshScreen,
                                        modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            stringResource(id = R.string.home_tap_to_load_content),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    Box(Modifier.fillMaxSize()) { /* empty screen */ }
                                    // there's currently an error showing, don't show any content
                                }
                            }
                            is HomeUiState.CityFound -> {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Search(onSearch = onSearch)
                                    TemperatureInformationHeader(location = uiState.weather)
                                    Box(modifier = Modifier.height(300.dp))
                                    BottomInfoCard()
                                }
                            }
                        }
                    })
            }
        }
    }

    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText = stringResource(id = errorMessage.messageId)
        val retryMessage = stringResource(id = R.string.retry)

        // If onRefreshPosts or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        val onRefreshPostsState by rememberUpdatedState(onRefreshScreen)
        val onErrorDismissState by rememberUpdatedState(onErrorDismissed)

        LaunchedEffect(errorMessageText, retryMessage, snackbarHostState) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessage
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshPostsState()
            }
            // Once the message is displayed and dismissed, notify the ViewModel
            onErrorDismissState(errorMessage.id)
        }
    }
}

@Composable
private fun Search(onSearch: (String) -> Unit) {
    SearchBar(
        hint = "Search city ...",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) { onSearch(it) }
}

