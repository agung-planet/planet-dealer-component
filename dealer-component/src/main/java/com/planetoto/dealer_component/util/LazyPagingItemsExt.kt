package com.planetoto.dealer_component.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun LazyPagingItems<*>.isFirstLoading() = itemCount == 0 && loadState.refresh == LoadState.Loading
fun LazyPagingItems<*>.isLoadedEmpty() =
    itemCount == 0 && loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached

fun LazyPagingItems<*>.isLoading() = loadState.refresh == LoadState.Loading
fun LazyPagingItems<*>.firstLoaded() = loadState.refresh is LoadState.NotLoading
fun LazyPagingItems<*>.hasAllPageLoaded() =
    loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached