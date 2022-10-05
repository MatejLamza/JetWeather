package com.example.jetweatherapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jetweatherapp.R
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
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
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
                    is HomeUiState.CityNotFound -> true
                    is HomeUiState.CityFound -> uiState.isLoading
                },
                emptyContent = { /*TODO*/ },
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
                                // there's currently an error showing, don't show any content
                                Box(Modifier.fillMaxSize()) { /* empty screen */ }
                            }
                        }
                        is HomeUiState.CityFound -> {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                SearchBar(
                                    hint = "Search city ...", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) { onSearch(it) }
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

