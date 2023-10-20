package com.planetoto.dealer_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor

/**
 * Cards contain contain content and actions that relate information about a subject. Filled cards
 * provide subtle separation from the background. This has less emphasis than elevated or outlined
 * cards.
 *
 * ![Filled card image](https://developer.android.com/images/reference/androidx/compose/material3/filled-card.png)
 *
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container, border (when [border] is not null), and
 * shadow (when using [elevation])
 * @param backgroundColor [DealerColor] that will be used for this card's background color.
 * @param elevation [CardElevation] used to resolve the elevation for this card in different states.
 * This controls the size of the shadow below the card. Additionally, when the container color is
 * [ColorScheme.surface], this controls the amount of primary color applied as an overlay. See also:
 * [Surface].
 * @param border the border to draw around the container of this card
 */
@Composable
fun GearsCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: DealerColor = DealerColor.White,
    elevation: Dp = 1.dp,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.color,
            disabledContainerColor = backgroundColor.color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            focusedElevation = elevation,
            hoveredElevation = elevation,
            draggedElevation = elevation,
            disabledElevation = elevation
        ),
        border = border,
        content = content
    )
}