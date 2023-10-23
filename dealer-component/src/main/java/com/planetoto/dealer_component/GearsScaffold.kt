package com.planetoto.dealer_component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Simple Scaffold
 *
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param topBar top app bar of the screen, typically a [GearsToolbar]
 * @param bottomBar bottom bar of the screen, typically a [NavigationBar]
 * @param snackbarHost component to host [Snackbar]s that are pushed to be shown via
 * [SnackbarHostState.showSnackbar], typically a [SnackbarHost]
 * @param floatingActionButton Main action button of the screen, typically a [FloatingActionButton]
 * @param floatingActionButtonPosition position of the FAB on the screen. See [FabPosition].
 * @param backgroundColor the color used for the background of this scaffold. Use [DealerColor.transparent]
 * to have no color.
 * @param contentWindowInsets window insets to be passed to [content] slot via [PaddingValues]
 * params. Scaffold will take the insets into account from the top/bottom only if the [topBar]/
 * [bottomBar] are not present, as the scaffold expect [topBar]/[bottomBar] to handle insets
 * instead
 * @param backgroundImage if background can not be passed using DealerColor, use this
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root via [Modifier.padding] and [Modifier.consumeWindowInsets] to
 * properly offset top and bottom bars. If using [Modifier.verticalScroll], apply this modifier to
 * the child of the scroll, and not on the scroll itself.
 */
@Composable
fun GearsScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: DealerColor = DealerColor.White,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    backgroundImage: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val navBarBackground = remember(isDark) {
        if (isDark) DealerColor.Black else DealerColor.White
    }

    backgroundImage?.let {
        Box(modifier = Modifier.fillMaxSize()) {
            it()
            Scaffold(
                modifier = modifier.navigationBarsPadding(),
                topBar = topBar,
                bottomBar = bottomBar,
                snackbarHost = snackbarHost,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                containerColor = Color.Transparent,
                contentWindowInsets = contentWindowInsets,
                content = content
            )
        }
    } ?: Scaffold(
        modifier = modifier.navigationBarsPadding(),
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = backgroundColor.color,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}

/**
 * Scaffold with Bottom Sheet support
 *
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param topBar top app bar of the screen, typically a [GearsToolbar]
 * @param bottomBar bottom bar of the screen, typically a [NavigationBar]
 * @param snackbarHost component to host [Snackbar]s that are pushed to be shown via
 * [SnackbarHostState.showSnackbar], typically a [SnackbarHost]
 * @param floatingActionButton Main action button of the screen, typically a [FloatingActionButton]
 * @param floatingActionButtonPosition position of the FAB on the screen. See [FabPosition].
 * @param backgroundColor the color used for the background of this scaffold. Use [DealerColor.transparent]
 * to have no color.
 * @param backgroundImage if background can not be passed using DealerColor, use this
 * @param state The state of the Scaffold, mainly used to control the bottom sheet
 * @param sheetShape The shape of the bottom sheet.
 * @param sheetBackgroundColor The color used for the background of this bottom sheet
 * @param sheetTonalElevation The tonal elevation of this bottom sheet.
 * @param scrimColor Color of the scrim that obscures content when the bottom sheet is open.
 * @param isSheetDraggable Make the bottom sheet can be dismissed by dragging it
 * @param tapOutsideSheetToDismiss Make the bottom sheet can be dismissed by tapping the [Scrim] area
 * @param sheetWindowInsets window insets to be passed to the bottom sheet window via [PaddingValues]
 * @param contentWindowInsets window insets to be passed to [content] slot via [PaddingValues]
 * params. Scaffold will take the insets into account from the top/bottom only if the [topBar]/
 * [bottomBar] are not present, as the scaffold expect [topBar]/[bottomBar] to handle insets
 * instead
 * @param sheetContent The content to be displayed inside the bottom sheet.
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root via [Modifier.padding] and [Modifier.consumeWindowInsets] to
 * properly offset top and bottom bars. If using [Modifier.verticalScroll], apply this modifier to
 * the child of the scroll, and not on the scroll itself.
 */
@Composable
fun <T : Any> GearsScaffold(
    modifier: Modifier = Modifier,
    state: GearsScaffoldState<T>,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: DealerColor = DealerColor.White,
    backgroundImage: (@Composable () -> Unit)? = null,
    sheetShape: Shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    sheetBackgroundColor: DealerColor = DealerColor.White,
    sheetTonalElevation: Dp = 1.dp,
    scrimColor: DealerColor = DealerColor.Black.alpha(0.8f),
    isSheetDraggable: Boolean = false,
    tapOutsideSheetToDismiss: Boolean = true,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    sheetWindowInsets: WindowInsets = GearsModalBottomSheetDefaults.NavigationBarWindowInsets,
    sheetContent: @Composable (T) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    GearsScaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        backgroundColor = backgroundColor,
        contentWindowInsets = contentWindowInsets,
        backgroundImage = backgroundImage
    ) {
        state.sheetType?.let {
            GearsModalBottomSheet(
                onDismissRequest = state::resetSheetType,
                sheetState = state.sheetState,
                shape = sheetShape,
                backgroundColor = sheetBackgroundColor,
                tonalElevation = sheetTonalElevation,
                scrimColor = scrimColor,
                showHandlebar = isSheetDraggable,
                tapOutsideToDismiss = tapOutsideSheetToDismiss,
                windowInsets = sheetWindowInsets
            ) {
                sheetContent(it)
            }
        }

        content(it)
    }
}

@Stable
class GearsScaffoldState<T : Any>(
    private val coroutineScope: CoroutineScope,
    initialSheetValue: GearsModalBottomSheetValue,
    initialSheetType: T?,
    private val onSheetStateChange: ((GearsModalBottomSheetValue) -> Unit)? = null
) {
    val sheetState = GearsModalBottomSheetState(initialValue = initialSheetValue)

    val isSheetVisible: Boolean
        get() = sheetState.isVisible

    var sheetType by mutableStateOf(initialSheetType)
        private set

    internal fun resetSheetType() {
        sheetType = null
        onSheetStateChange?.invoke(GearsModalBottomSheetValue.Hidden)
    }

    fun showBottomSheet(type: T) {
        coroutineScope.launch {
            sheetType = type
        }.invokeOnCompletion {
            onSheetStateChange?.invoke(GearsModalBottomSheetValue.Expanded)
        }
    }

    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!isSheetVisible) {
                resetSheetType()
            }
        }
    }

    companion object {
        /**
         * The default [Saver] implementation for [GearsScaffoldState].
         */
        fun <T : Any> Saver(
            coroutineScope: CoroutineScope,
            initialSheetType: T?,
            onSheetStateChange: ((GearsModalBottomSheetValue) -> Unit)?
        ) = Saver<GearsScaffoldState<T>, GearsModalBottomSheetValue>(
            save = { it.sheetState.currentValue },
            restore = { savedValue ->
                GearsScaffoldState(
                    coroutineScope = coroutineScope,
                    initialSheetValue = savedValue,
                    initialSheetType = initialSheetType,
                    onSheetStateChange = onSheetStateChange
                )
            }
        )
    }
}

@Composable
fun <T : Any> rememberGearsScaffoldState(
    initialSheetType: T?,
    initialSheetValue: GearsModalBottomSheetValue = GearsModalBottomSheetValue.Hidden,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onSheetStateChange: ((GearsModalBottomSheetValue) -> Unit)? = null
): GearsScaffoldState<T> {
    return rememberSaveable(
        saver = GearsScaffoldState.Saver(
            coroutineScope = coroutineScope,
            initialSheetType = initialSheetType,
            onSheetStateChange = onSheetStateChange
        )
    ) {
        GearsScaffoldState(
            coroutineScope = coroutineScope,
            initialSheetValue = initialSheetValue,
            initialSheetType = initialSheetType,
            onSheetStateChange = onSheetStateChange
        )
    }
}