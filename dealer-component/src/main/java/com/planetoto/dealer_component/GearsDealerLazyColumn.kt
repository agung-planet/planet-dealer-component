package com.planetoto.dealer_component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.planetoto.dealer_component.theme.DealerColor
import com.planetoto.dealer_component.util.isLoadedEmpty
import com.planetoto.dealer_component.util.isLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @param coroutineScope: Coroutine scope for retry if data failed to fetch
 * @param itemsToListenState: Main items to listen the state
 * @param isOverScrollMode: lazy column animation when over scrolling (bounced effect)
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GearsDealerLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemsToListenState: LazyPagingItems<*>,
    isOverScrollMode: Boolean = false,
    onLoadingFirstPage: @Composable (LazyItemScope.() -> Unit) = {
        Box(
            modifier = Modifier.fillParentMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    },
    onEmptyLoad: @Composable (() -> Unit) = {},
    content: LazyListScope.() -> Unit
) {
    if (itemsToListenState.isLoadedEmpty()) {
        onEmptyLoad()
    } else {
        val overScrollConfiguration = if (isOverScrollMode) OverscrollConfiguration() else null
        CompositionLocalProvider(LocalOverscrollConfiguration provides overScrollConfiguration) {
            LazyColumn(
                modifier,
                state,
                contentPadding,
                reverseLayout,
                verticalArrangement,
                horizontalAlignment,
                flingBehavior
            ) {
                itemsToListenState.apply {
                    if (isLoading()) {
                        item(content = onLoadingFirstPage)
                    } else {
                        content()
                    }

                    when {
                        loadState.append is LoadState.Loading -> item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )

                                GearsDealerText(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = stringResource(id = R.string.loading),
                                    type = GearsDealerTextType.Text14,
                                    textColor = DealerColor.Ink20
                                )
                            }
                        }

                        loadState.append is LoadState.Error || loadState.refresh is LoadState.Error -> {
                            coroutineScope.launch {
                                delay(5000)
                                retry()
                            }
                        }
                    }
                }
            }
        }
    }
}