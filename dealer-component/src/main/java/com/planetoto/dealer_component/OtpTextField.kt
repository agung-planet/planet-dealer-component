package com.planetoto.dealer_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.dealer_component.theme.DealerColor
import com.planetoto.dealer_component.util.CursorOnLastIndexVisualTransformation

@ExperimentalComposeUiApi
@Composable
fun OtpTextField(
    isTablet: Boolean,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onComplete: () -> Unit
) {
    var otpSent by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val (first, second, third, fourth) = FocusRequester.createRefs()

    val space = if (isTablet) 40.dp else 27.dp

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(space)) {
        repeat(4) { i ->
            val otpCodeFieldModifier = Modifier.weight(1f).let {
                when (i) {
                    0 -> it
                        .focusRequester(first)
                        .focusProperties {
                            down = second
                        }

                    1 -> it
                        .focusRequester(second)
                        .focusProperties {
                            down = third
                            up = first
                        }

                    2 -> it
                        .focusRequester(third)
                        .focusProperties {
                            down = fourth
                            up = second
                        }

                    3 -> it
                        .focusRequester(fourth)
                        .focusProperties {
                            up = third
                        }

                    else -> it
                }
            }

            OtpCodeTextField(
                modifier = otpCodeFieldModifier,
                focusManager = focusManager,
                isError = isError,
                value = value.getOrNull(i)?.toString().orEmpty(),
                onValueChanged = { newStr ->
                    try {
                        val str = value.toMutableList()
                        val oldValue = str.getOrNull(i)
                        val newValue = newStr.toIntOrNull()?.toString().orEmpty()

                        if (oldValue != null) {
                            str[i] = if (newValue.isBlank()) Char.MIN_VALUE else newValue.first()
                        } else {
                            str.add(newStr.first())
                        }
                        onValueChanged(str.joinToString(""))

                        // switch focus automagically
                        if (newStr.isNotBlank()) {
                            if (i < 3) {
                                focusManager.moveFocus(FocusDirection.Down)
                            } else {
                                focusManager.clearFocus(true)
                            }
                        }

                        otpSent = false
                    } catch (e: Exception) {
                    }
                }
            )

            if (value.length == 4 && !value.contains(Char.MIN_VALUE) && !otpSent) {
                onComplete()
                otpSent = true
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun OtpCodeTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColorId = when {
        isError -> DealerColor.PlanetRed
        isFocused -> DealerColor.TextFieldFocusedStroke
        value.isNotBlank() -> DealerColor.TextFieldFilledStroke
        else -> DealerColor.TextFieldEmptyStroke
    }
    val backgroundId =
        if (value.isNotBlank()) DealerColor.TextFieldFilledBackground else DealerColor.White

    BasicTextField(
        value = value,
        onValueChange = { s ->
            val filtered = s.filter { it != Char.MIN_VALUE }
            if (filtered.length <= 1) {
                onValueChanged(filtered)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        singleLine = true,
        textStyle = TextStyle(
            color = DealerColor.Ink100.color,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font((R.font.inter_semi_bold))),
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        ),
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundId.color)
            .border(BorderStroke(1.dp, borderColorId.color), RoundedCornerShape(16.dp))
            .onFocusChanged {
                isFocused = it.isFocused || it.hasFocus
            }
            .onKeyEvent {
                val isBackspace = it.type == KeyEventType.KeyUp && it.key == Key.Backspace
                val isEmpty = value.isBlank() || value.contains(Char.MIN_VALUE)

                if (isBackspace && isEmpty) {
                    focusManager.moveFocus(FocusDirection.Up)
                    true
                } else false
            },
        visualTransformation = CursorOnLastIndexVisualTransformation,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {
                innerTextField()
            }
        }
    )
}
