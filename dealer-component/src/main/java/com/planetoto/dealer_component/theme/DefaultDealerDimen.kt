package com.planetoto.dealer_component.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DefaultDealerDimen(
    val defaultCardRadius: Dp = 16.dp,
    val largeIconSize: Dp = 40.dp,
    val mediumIconSize: Dp = 24.dp,
    val smallIconSize: Dp = 16.dp
)

val DealerDimen = compositionLocalOf { DefaultDealerDimen() }