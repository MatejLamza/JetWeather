package com.example.jetweatherapp.common.components

import androidx.compose.runtime.Composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) emptyContent()
    else SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = loading),
        onRefresh = onRefresh,
        content = content
    )
}
