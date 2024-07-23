package com.planetoto.dealer_component.ptr

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * The default indicator for Compose pull-to-refresh, based on Android's SwipeRefreshLayout.
 *
 * @sample androidx.compose.material.samples.PullRefreshSample
 *
 * @param refreshing A boolean representing whether a refresh is occurring.
 * @param state The [DealerPullRefreshState] which controls where and how the indicator will be drawn.
 * @param modifier Modifiers for the indicator.
 * @param backgroundColor The color of the indicator's background.
 * @param contentColor The color of the indicator's arc and arrow.
 * @param scale A boolean controlling whether the indicator's size scales with pull progress or not.
 */
@Composable
fun DealerPullRefreshIndicator(
    refreshing: Boolean,
    state: DealerPullRefreshState,
    modifier: Modifier = Modifier,
    backgroundColor: DealerColor = DealerColor.White,
    contentColor: DealerColor = DealerColor.PlanetBlue,
    scale: Boolean = false
) {
    val showElevation by remember(refreshing, state) {
        derivedStateOf { refreshing || state.position > 0.5f }
    }

    // Apply an elevation overlay if needed. Note that we aren't using Surface here, as we do not
    // want its input-blocking behaviour, since the indicator is typically displayed above other
    // (possibly) interactive content.
    val color = generateElevationOverlay(color = backgroundColor.color, elevation = Elevation)

    Box(
        modifier = modifier
            .size(IndicatorSize)
            .dealerPullRefreshIndicatorTransform(state, scale)
            .shadow(if (showElevation) Elevation else 0.dp, SpinnerShape, clip = true)
            .background(color = color, shape = SpinnerShape)
    ) {
        Crossfade(
            targetState = refreshing,
            animationSpec = tween(durationMillis = CrossfadeDurationMs)
        ) { refreshing ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val spinnerSize = (ArcRadius + StrokeWidth).times(2)

                if (refreshing) {
                    CircularProgressIndicator(
                        color = contentColor.color,
                        strokeWidth = StrokeWidth,
                        modifier = Modifier.size(spinnerSize),
                    )
                } else {
                    CircularArrowIndicator(state, contentColor.color, Modifier.size(spinnerSize))
                }
            }
        }
    }
}

/**
 * Modifier.size MUST be specified.
 */
@Composable
private fun CircularArrowIndicator(
    state: DealerPullRefreshState,
    color: Color,
    modifier: Modifier,
) {
    val path = remember { Path().apply { fillType = PathFillType.EvenOdd } }

    val targetAlpha by remember(state) {
        derivedStateOf {
            if (state.progress >= 1f) MaxAlpha else MinAlpha
        }
    }

    val alphaState = animateFloatAsState(targetValue = targetAlpha, animationSpec = AlphaTween)

    // Empty semantics for tests
    Canvas(modifier.semantics {}) {
        val values = ArrowValues(state.progress)
        val alpha = alphaState.value

        rotate(degrees = values.rotation) {
            val arcRadius = ArcRadius.toPx() + StrokeWidth.toPx() / 2f
            val arcBounds = Rect(
                size.center.x - arcRadius,
                size.center.y - arcRadius,
                size.center.x + arcRadius,
                size.center.y + arcRadius
            )
            drawArc(
                color = color,
                alpha = alpha,
                startAngle = values.startAngle,
                sweepAngle = values.endAngle - values.startAngle,
                useCenter = false,
                topLeft = arcBounds.topLeft,
                size = arcBounds.size,
                style = Stroke(
                    width = StrokeWidth.toPx(),
                    cap = StrokeCap.Square
                )
            )
            drawArrow(path, arcBounds, color, alpha, values)
        }
    }
}

@Immutable
private class ArrowValues(
    val rotation: Float,
    val startAngle: Float,
    val endAngle: Float,
    val scale: Float
)

private fun ArrowValues(progress: Float): ArrowValues {
    // Discard first 40% of progress. Scale remaining progress to full range between 0 and 100%.
    val adjustedPercent = max(min(1f, progress) - 0.4f, 0f) * 5 / 3
    // How far beyond the threshold pull has gone, as a percentage of the threshold.
    val overshootPercent = abs(progress) - 1.0f
    // Limit the overshoot to 200%. Linear between 0 and 200.
    val linearTension = overshootPercent.coerceIn(0f, 2f)
    // Non-linear tension. Increases with linearTension, but at a decreasing rate.
    val tensionPercent = linearTension - linearTension.pow(2) / 4

    // Calculations based on SwipeRefreshLayout specification.
    val endTrim = adjustedPercent * MaxProgressArc
    val rotation = (-0.25f + 0.4f * adjustedPercent + tensionPercent) * 0.5f
    val startAngle = rotation * 360
    val endAngle = (rotation + endTrim) * 360
    val scale = min(1f, adjustedPercent)

    return ArrowValues(rotation, startAngle, endAngle, scale)
}

private fun DrawScope.drawArrow(
    arrow: Path,
    bounds: Rect,
    color: Color,
    alpha: Float,
    values: ArrowValues
) {
    arrow.reset()
    arrow.moveTo(0f, 0f) // Move to left corner
    arrow.lineTo(x = ArrowWidth.toPx() * values.scale, y = 0f) // Line to right corner

    // Line to tip of arrow
    arrow.lineTo(
        x = ArrowWidth.toPx() * values.scale / 2,
        y = ArrowHeight.toPx() * values.scale
    )

    val radius = min(bounds.width, bounds.height) / 2f
    val inset = ArrowWidth.toPx() * values.scale / 2f
    arrow.translate(
        Offset(
            x = radius + bounds.center.x - inset,
            y = bounds.center.y + StrokeWidth.toPx() / 2f
        )
    )
    arrow.close()
    rotate(degrees = values.endAngle) {
        drawPath(path = arrow, color = color, alpha = alpha)
    }
}

@ReadOnlyComposable
@Composable
private fun generateElevationOverlay(color: Color, elevation: Dp): Color {
    return if (elevation > 0.dp) {
        val foregroundColor = calculateForegroundColor(color, elevation)
        foregroundColor.compositeOver(color)
    } else {
        color
    }
}

@ReadOnlyComposable
@Composable
private fun calculateForegroundColor(backgroundColor: Color, elevation: Dp): Color {
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    val baseForegroundColor = androidx.compose.material3.contentColorFor(backgroundColor)
    return baseForegroundColor.copy(alpha = alpha)
}

private const val CrossfadeDurationMs = 100
private const val MaxProgressArc = 0.8f

private val IndicatorSize = 40.dp
private val SpinnerShape = CircleShape
private val ArcRadius = 7.5.dp
private val StrokeWidth = 2.5.dp
private val ArrowWidth = 10.dp
private val ArrowHeight = 5.dp
private val Elevation = 6.dp

// Values taken from SwipeRefreshLayout
private const val MinAlpha = 0.3f
private const val MaxAlpha = 1f
private val AlphaTween = tween<Float>(300, easing = LinearEasing)