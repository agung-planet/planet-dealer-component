package com.planetoto.dealer_component.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun DealerTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(DealerDimen provides DefaultDealerDimen()) {
        content()
    }
}