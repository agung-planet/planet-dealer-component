package com.planetoto.dealer_component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.planetoto.dealer_component.theme.DealerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GearsDealerPullToRefresh(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    if (state.isRefreshing) {
        LaunchedEffect(key1 = Unit) {
            onRefresh()
            state.endRefresh()
        }
    }

    Box(modifier = modifier.nestedScroll(state.nestedScrollConnection)) {
        content()
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
            containerColor = DealerColor.White.color,
            contentColor = DealerColor.PlanetBlue.color
        )
    }
}