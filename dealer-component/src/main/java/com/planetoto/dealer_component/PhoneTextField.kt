package com.planetoto.dealer_component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.dealer_component.theme.DealerColor
import com.planetoto.dealer_component.util.PhoneNumberSeparatorVisualTransformation

@Composable
fun PhoneTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    prefixText: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColorId by remember {
        derivedStateOf {
            when {
                isError -> DealerColor.AccentRed
                isFocused -> DealerColor.AccentBlue
                else -> DealerColor.Ink60
            }
        }
    }
    val defaultBackgroundColorId by remember {
        derivedStateOf {
            when {
                isError -> DealerColor.TextFieldErrorBackground
                isFocused -> DealerColor.BlueBackground
                else -> DealerColor.Ink10
            }
        }
    }
    val textFieldBackgroundColorId =
        if (isError) DealerColor.TextFieldFilledBackground else DealerColor.White
    val prefixTextColor by remember {
        derivedStateOf {
            when {
                isError -> DealerColor.Ink60
                isFocused -> DealerColor.BlueDarkest
                else -> DealerColor.Ink100
            }
        }
    }
    val textColorId = if (isError) DealerColor.AccentRed else DealerColor.Ink100

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
        prefixText?.let {
            Box(
                modifier = Modifier.padding(horizontal = 13.dp)
            ) {
                Text(
                    text = prefixText,
                    color = prefixTextColor.color,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font((R.font.inter_semi_bold))),
                    lineHeight = 21.sp
                )
            }

            GearsDivider(color = borderColorId, orientation = GearsDividerOrientation.Vertical)
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .weight(1f)
                .background(textFieldBackgroundColorId.color)
                .padding(15.dp)
                .onFocusChanged {
                    isFocused = it.isFocused || it.hasFocus
                },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            cursorBrush = SolidColor(DealerColor.AccentBlue.color),
            textStyle = TextStyle(
                color = textColorId.color,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font((R.font.inter_semi_bold))),
                lineHeight = 21.sp
            ),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty() && !placeholder.isNullOrBlank()) {
                        Text(
                            text = placeholder,
                            color = DealerColor.Ink40.color,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font((R.font.inter_regular))),
                            lineHeight = 24.sp,
                            maxLines = 1,
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        )
                    }

                    innerTextField()
                }
            },
            visualTransformation = PhoneNumberSeparatorVisualTransformation()
        )
    }
}