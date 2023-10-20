/*
 * Copyright 2023 PLANET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.planetoto.dealer_component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue.Expanded
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException

/**
 * State of a sheet composable, such as [GearsModalBottomSheet]
 *
 * Contains states relating to it's swipe position as well as animations between state values.
 *
 * @param initialValue The initial value of the state.
 */
@Stable
class GearsModalBottomSheetState(initialValue: GearsModalBottomSheetValue = GearsModalBottomSheetValue.Hidden) {
    internal var swipeableState = GearsSwipeableV2State(
        initialValue = initialValue,
        animationSpec = GearsSwipeableV2Defaults.AnimationSpec,
        confirmValueChange = { true }
    )

    internal val offset: Float? get() = swipeableState.offset

    /**
     * The current value of the state.
     *
     * If no swipe or animation is in progress, this corresponds to the state the bottom sheet is
     * currently in. If a swipe or an animation is in progress, this corresponds the state the sheet
     * was in before the swipe or animation started.
     */
    val currentValue: GearsModalBottomSheetValue get() = swipeableState.currentValue

    /**
     * The target value of the bottom sheet state.
     *
     * If a swipe is in progress, this is the value that the sheet would animate to if the
     * swipe finishes. If an animation is running, this is the target value of that animation.
     * Finally, if no swipe or animation is in progress, this is the same as the [currentValue].
     */
    val targetValue: GearsModalBottomSheetValue get() = swipeableState.targetValue

    /**
     * Whether the modal bottom sheet is visible.
     */
    val isVisible: Boolean
        get() = swipeableState.currentValue != GearsModalBottomSheetValue.Hidden

    /**
     * Require the current offset (in pixels) of the bottom sheet.
     *
     * The offset will be initialized during the first measurement phase of the provided sheet
     * content.
     *
     * These are the phases:
     * Composition { -> Effects } -> Layout { Measurement -> Placement } -> Drawing
     *
     * During the first composition, an [IllegalStateException] is thrown. In subsequent
     * compositions, the offset will be derived from the anchors of the previous pass. Always prefer
     * accessing the offset from a LaunchedEffect as it will be scheduled to be executed the next
     * frame, after layout.
     *
     * @throws IllegalStateException If the offset has not been initialized yet
     */
    fun requireOffset(): Float = swipeableState.requireOffset()

    /**
     * Expand the bottom sheet with animation and suspend until it is [Expanded].
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun show() {
        animateTo(GearsModalBottomSheetValue.Expanded)
    }

    /**
     * Hide the bottom sheet with animation and suspend until it is fully hidden or animation has
     * been cancelled.
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun hide() {
        animateTo(GearsModalBottomSheetValue.Hidden)
    }

    /**
     * Animate to a [targetValue].
     * If the [targetValue] is not in the set of anchors, the [currentValue] will be updated to the
     * [targetValue] without updating the offset.
     *
     * @throws CancellationException if the interaction interrupted by another interaction like a
     * gesture interaction or another programmatic interaction like a [animateTo] call.
     *
     * @param targetValue The target value of the animation
     */
    internal suspend fun animateTo(
        targetValue: GearsModalBottomSheetValue,
        velocity: Float = swipeableState.lastVelocity
    ) {
        swipeableState.animateTo(targetValue, velocity)
    }

    /**
     * Snap to a [targetValue] without any animation.
     *
     * @throws CancellationException if the interaction interrupted by another interaction like a
     * gesture interaction or another programmatic interaction like a [animateTo] or [snapTo] call.
     *
     * @param targetValue The target value of the animation
     */
    internal suspend fun snapTo(targetValue: GearsModalBottomSheetValue) {
        swipeableState.snapTo(targetValue)
    }

    /**
     * Attempt to snap synchronously. Snapping can happen synchronously when there is no other swipe
     * transaction like a drag or an animation is progress. If there is another interaction in
     * progress, the suspending [snapTo] overload needs to be used.
     *
     * @return true if the synchronous snap was successful, or false if we couldn't snap synchronous
     */
    internal fun trySnapTo(targetValue: GearsModalBottomSheetValue) =
        swipeableState.trySnapTo(targetValue)

    /**
     * Find the closest anchor taking into account the velocity and settle at it with an animation.
     */
    internal suspend fun settle(velocity: Float) {
        swipeableState.settle(velocity)
    }

    companion object {
        /**
         * The default [Saver] implementation for [SheetState].
         */
        fun Saver() = Saver<GearsModalBottomSheetState, GearsModalBottomSheetValue>(
            save = { it.currentValue },
            restore = { savedValue ->
                GearsModalBottomSheetState(savedValue)
            }
        )
    }
}

/**
 * Possible values of [SheetState].
 */
enum class GearsModalBottomSheetValue {
    /**
     * The sheet is not visible.
     */
    Hidden,

    /**
     * The sheet is visible at full height.
     */
    Expanded
}

internal fun ConsumeSwipeWithinBottomSheetBoundsNestedScrollConnection(
    sheetState: GearsModalBottomSheetState,
    orientation: Orientation,
    onFling: (velocity: Float) -> Unit
): NestedScrollConnection = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.toFloat()
        return if (delta < 0 && source == NestedScrollSource.Drag) {
            sheetState.swipeableState.dispatchRawDelta(delta).toOffset()
        } else {
            Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return if (source == NestedScrollSource.Drag) {
            sheetState.swipeableState.dispatchRawDelta(available.toFloat()).toOffset()
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        val toFling = available.toFloat()
        val currentOffset = sheetState.requireOffset()
        return if (toFling < 0 && currentOffset > sheetState.swipeableState.minOffset) {
            onFling(toFling)
            // since we go to the anchor with tween settling, consume all for the best UX
            available
        } else {
            Velocity.Zero
        }
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        onFling(available.toFloat())
        return available
    }

    private fun Float.toOffset(): Offset = Offset(
        x = if (orientation == Orientation.Horizontal) this else 0f,
        y = if (orientation == Orientation.Vertical) this else 0f
    )

    @JvmName("velocityToFloat")
    private fun Velocity.toFloat() = if (orientation == Orientation.Horizontal) x else y

    @JvmName("offsetToFloat")
    private fun Offset.toFloat(): Float = if (orientation == Orientation.Horizontal) x else y
}

@Composable
internal fun rememberSheetState(initialValue: GearsModalBottomSheetValue = GearsModalBottomSheetValue.Hidden): GearsModalBottomSheetState {
    return rememberSaveable(saver = GearsModalBottomSheetState.Saver()) {
        GearsModalBottomSheetState(initialValue)
    }
}

internal val BottomSheetMaxWidth = 640.dp