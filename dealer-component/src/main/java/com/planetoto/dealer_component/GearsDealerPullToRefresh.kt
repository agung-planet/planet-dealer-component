package com.planetoto.dealer_component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.planetoto.dealer_component.ptr.DealerPullRefreshIndicator
import com.planetoto.dealer_component.ptr.DealerPullRefreshState
import com.planetoto.dealer_component.ptr.dealerPullRefresh

@Composable
fun GearsDealerPullToRefresh(
    modifier: Modifier = Modifier,
    state: DealerPullRefreshState,
    refreshing: Boolean,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.dealerPullRefresh(
            state = state,
            enabled = enabled
        )
    ) {
        content()
        DealerPullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = state
        )
    }
}