package com.example.jetweatherapp.ui.home

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeRoute(
        uiState = uiState,
        onRefreshScreen = { homeViewModel.refresh() },
        onErrorDismiss = { homeViewModel.errorShown(it) },
        onSearch = { homeViewModel.fetchTemperature(it) },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onRefreshScreen: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onSearch: (String) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    HomeScreen(
        uiState = uiState,
        onRefreshScreen = onRefreshScreen,
        onErrorDismissed = onErrorDismiss,
        onSearch = onSearch,
        snackbarHostState = snackbarHostState
    )
}
