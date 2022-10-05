package com.example.jetweatherapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeRoute(uiState = uiState,
        onRefreshScreen = { homeViewModel.refresh() },
        onErrorDismiss = { homeViewModel.errorShown(it) },
        onSearch = { homeViewModel.fetchTemperature(it) }
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onRefreshScreen: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onSearch: (String) -> Unit
) {
    HomeScreen(
        uiState = uiState,
        onRefreshScreen = onRefreshScreen,
        onErrorDismissed = onErrorDismiss,
        onSearch = onSearch
    )
}
