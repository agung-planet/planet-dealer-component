package com.planetoto.dealer_component.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
fun getStatusBarHeight(): Dp {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    return systemBarsPadding.calculateTopPadding()
}