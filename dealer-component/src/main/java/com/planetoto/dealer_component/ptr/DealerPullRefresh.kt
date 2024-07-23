package com.planetoto.dealer_component.ptr

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DealerPullRefresh(
    modifier: Modifier = Modifier,
    pullRefreshState: DealerPullRefreshState,
    refreshing: Boolean,
    enabledPullToRefresh: Boolean = true,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.dealerPullRefresh(
            state = pullRefreshState,
            enabled = enabledPullToRefresh
        )
    ) {
        content()
        DealerPullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = pullRefreshState
        )
    }
}
