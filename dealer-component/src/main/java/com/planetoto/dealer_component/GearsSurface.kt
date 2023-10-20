package com.planetoto.dealer_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor

@Composable
fun GearsSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: DealerColor = DealerColor.White,
    contentColor: DealerColor = DealerColor.Ink100,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor.color,
        contentColor = contentColor.color,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
        content = content
    )
}