package com.planetoto.dealer_component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.DefaultModelEqualityDelegate
import coil.compose.EqualityDelegate
import com.mxalbert.zoomable.Zoomable
import com.mxalbert.zoomable.ZoomableState
import com.valentinilk.shimmer.shimmer

@Composable
fun GearsDealerImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    error: Painter? = null,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    clipToBounds: Boolean = true,
    modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate
) {
    var showShimmer by remember { mutableStateOf(true) }

    AsyncImage(
        modifier = modifier.then(if (showShimmer) Modifier.shimmer() else Modifier),
        model = imageUrl,
        contentDescription = contentDescription,
        error = error,
        onLoading = {
            showShimmer = true
            onLoading?.invoke(it)
        },
        onSuccess = {
            showShimmer = false
            onSuccess?.invoke(it)
        },
        onError = {
            showShimmer = false
            onError?.invoke(it)
        },
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        clipToBounds = clipToBounds,
        modelEqualityDelegate = modelEqualityDelegate
    )
}

@Composable
fun GearsDealerImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    state: ZoomableState,
    contentScale: ContentScale = ContentScale.Crop,
    error: Painter? = null,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    clipToBounds: Boolean = true,
    modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate,
    enableZoom: Boolean = true,
    onTap: ((Offset) -> Unit)? = null,
    dismissGestureEnabled: Boolean = false,
    onDismiss: () -> Boolean = { false }
) {
    Zoomable(
        modifier = modifier,
        state = state,
        enabled = enableZoom,
        onTap = onTap,
        onDismiss = onDismiss,
        dismissGestureEnabled = dismissGestureEnabled
    ) {
        GearsDealerImage(
            modifier = modifier,
            imageUrl = imageUrl,
            contentScale = contentScale,
            error = error,
            onLoading = onLoading,
            onSuccess = onSuccess,
            onError = onError,
            alignment = alignment,
            alpha = alpha,
            contentDescription = contentDescription,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
            clipToBounds = clipToBounds,
            modelEqualityDelegate = modelEqualityDelegate
        )
    }
}