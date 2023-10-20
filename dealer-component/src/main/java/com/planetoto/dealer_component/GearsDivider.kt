package com.planetoto.dealer_component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor

enum class GearsDividerOrientation {
    Horizontal, Vertical
}

@Composable
fun GearsDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: DealerColor = DealerColor.Ink60,
    orientation: GearsDividerOrientation = GearsDividerOrientation.Horizontal
) {
    when (orientation) {
        GearsDividerOrientation.Horizontal -> {
            Divider(modifier = modifier, thickness = thickness, color = color.color)
        }

        GearsDividerOrientation.Vertical -> {
            Divider(
                modifier = modifier
                    .width(thickness)
                    .fillMaxHeight(),
                color = color.color
            )
        }
    }
}