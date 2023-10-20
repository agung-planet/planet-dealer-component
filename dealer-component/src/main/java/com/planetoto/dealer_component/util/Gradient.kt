package com.planetoto.dealer_component.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor

sealed class GradientBackgroundType(
    val additionalOffsetX: Float,
    val additionalOffsetY: Float,
    val angelDegrees: Float
) {
    object GradientHorizontal : GradientBackgroundType(0.1f, 0.95f, 139.71f)
    object GradientVertical : GradientBackgroundType(0f, 0.95f, 170.75f)
}

@Composable
fun Modifier.gradientBackground(
    type: GradientBackgroundType = GradientBackgroundType.GradientVertical,
    shape: Shape = RectangleShape,
    alpha: Float = 1.0f
) = this.then(
    background(
        Brush.linearGradient(
            -0.25f to DealerColor.PrimaryGradient.color,
            1.84f to DealerColor.SecondaryGradient.color,
            additionalOffsetX = type.additionalOffsetX,
            additionalOffsetY = type.additionalOffsetY,
            angleInDegrees = type.angelDegrees,
            useAsCssAngle = true
        ),
        shape = shape,
        alpha = alpha
    )
)

@Composable
@Preview
fun PreviewGradientBackground() {
    Column {
        Box(
            modifier = Modifier
                .gradientBackground(GradientBackgroundType.GradientVertical)
                .size(200.dp, 500.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .gradientBackground(
                    GradientBackgroundType.GradientHorizontal,
                    shape = RoundedCornerShape(16.dp)
                )
                .size(500.dp, 200.dp)
        )
    }
}