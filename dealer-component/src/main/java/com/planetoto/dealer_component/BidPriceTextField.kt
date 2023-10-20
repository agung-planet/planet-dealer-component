package com.planetoto.dealer_component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.dealer_component.theme.DealerColor
import com.planetoto.dealer_component.util.ThousandSeparatorVisualTransformation

@ExperimentalComposeUiApi
@Composable
fun BidPriceTextField(
    price: String,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    setPrice: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColorId by remember(errorMessage) {
        derivedStateOf {
            when {
                errorMessage != null -> DealerColor.AccentRed
                isFocused -> DealerColor.TextFieldFocusedStroke
                else -> DealerColor.Ink60
            }
        }
    }
    val defaultBackgroundColorId by remember(errorMessage) {
        derivedStateOf {
            when {
                errorMessage != null -> DealerColor.TextFieldErrorBackground
                isFocused -> DealerColor.TextFieldFocusedBackground
                else -> DealerColor.Ink10
            }
        }
    }
    val textFieldBackgroundColorId = remember(errorMessage) {
        if (errorMessage != null) DealerColor.TextFieldFilledBackground else DealerColor.White
    }
    val prefixTextColor by remember(errorMessage) {
        derivedStateOf {
            when {
                errorMessage != null -> DealerColor.Ink60
                isFocused -> DealerColor.BlueDarkest
                else -> DealerColor.Ink100
            }
        }
    }
    val textColorId = remember(errorMessage) {
        if (errorMessage != null) {
            DealerColor.AccentRed
        } else {
            DealerColor.Ink100
        }
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(defaultBackgroundColorId.color)
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = borderColorId.color,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 13.dp)
        ) {
            Text(
                text = "Rp",
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = prefixTextColor.color
            )
        }

        GearsDivider(color = borderColorId, orientation = GearsDividerOrientation.Vertical)

        BasicTextField(
            value = price,
            onValueChange = {
                val text = it.trimStart('0').take(18).toLongOrNull()?.toString()
                setPrice(text.orEmpty())
            },
            modifier = Modifier
                .weight(1f)
                .background(textFieldBackgroundColorId.color)
                .padding(vertical = 13.dp, horizontal = 9.dp)
                .onFocusChanged {
                    isFocused = it.isFocused || it.hasFocus
                },
            singleLine = true,
            cursorBrush = SolidColor(DealerColor.AccentBlue.color),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            textStyle = TextStyle(
                color = textColorId.color,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                lineHeight = 24.2.sp
            ),
            visualTransformation = ThousandSeparatorVisualTransformation()
        )
    }
}