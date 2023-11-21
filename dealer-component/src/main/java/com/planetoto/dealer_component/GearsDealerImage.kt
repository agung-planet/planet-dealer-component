package com.planetoto.dealer_component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.request.ImageRequest
import com.planetoto.dealer_component.theme.DealerColor
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.coil.CoilImageState
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun GearsDealerImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    imageLoader: @Composable (() -> ImageLoader)? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    contentDescription: String? = null,
    requestListener: (() -> ImageRequest.Listener)? = null,
    onImageStateChanged: (CoilImageState) -> Unit = {},
    success: @Composable (BoxScope.(imageState: CoilImageState.Success, painter: Painter) -> Unit)? = null,
    error: @Composable (BoxScope.(imageState: CoilImageState.Failure) -> Unit)? = null,
    @DrawableRes previewPlaceholder: Int = 0,
    shimmerBaseColor: DealerColor? = DealerColor.Ink20,
    shimmerHighlightColor: DealerColor? = DealerColor.Ink40
) {
    val component = rememberImageComponent {
        if (shimmerHighlightColor != null && shimmerBaseColor != null) {
            +ShimmerPlugin(
                baseColor = shimmerBaseColor.color,
                highlightColor = shimmerHighlightColor.color
            )
        }
    }

    if (imageLoader != null) {
        CoilImage(
            imageModel = { imageUrl },
            modifier = modifier,
            imageOptions = ImageOptions(
                alignment = alignment,
                contentDescription = contentDescription,
                contentScale = contentScale,
                alpha = alpha
            ),
            imageLoader = imageLoader,
            requestListener = requestListener,
            component = component,
            onImageStateChanged = onImageStateChanged,
            success = success,
            failure = error,
            previewPlaceholder = previewPlaceholder
        )
    } else {
        CoilImage(
            imageModel = { imageUrl },
            modifier = modifier,
            imageOptions = ImageOptions(
                alignment = alignment,
                contentDescription = contentDescription,
                contentScale = contentScale,
                alpha = alpha
            ),
            requestListener = requestListener,
            component = component,
            onImageStateChanged = onImageStateChanged,
            success = success,
            failure = error,
            previewPlaceholder = previewPlaceholder
        )
    }
}