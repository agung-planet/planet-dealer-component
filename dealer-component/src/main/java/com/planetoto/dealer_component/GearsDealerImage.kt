package com.planetoto.dealer_component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import com.planetoto.dealer_component.theme.DealerColor
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun GearsDealerImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    imageLoader: ImageLoader? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    contentDescription: String? = null,
    circularReveal: CircularReveal? = null,
    error: Any? = null,
    shimmerParams: ShimmerParams = ShimmerParams(
        baseColor = DealerColor.Ink20.color,
        highlightColor = DealerColor.Ink40.color
    )
) {
    if (imageLoader != null) {
        CoilImage(
            imageModel = imageUrl,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            contentDescription = contentDescription,
            circularReveal = circularReveal,
            error = error,
            imageLoader = imageLoader,
            shimmerParams = shimmerParams
        )
    } else {
        CoilImage(
            imageModel = imageUrl,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            contentDescription = contentDescription,
            circularReveal = circularReveal,
            error = error,
            shimmerParams = shimmerParams
        )
    }
}