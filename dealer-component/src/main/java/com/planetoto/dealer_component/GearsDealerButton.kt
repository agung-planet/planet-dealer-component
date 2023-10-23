package com.planetoto.dealer_component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BasicDealerButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: DealerColor = DealerColor.White,
    textColor: DealerColor = DealerColor.Ink100,
    disableBackgroundColor: DealerColor = DealerColor.ButtonDisabledBackground,
    disableTextColor: DealerColor = DealerColor.ButtonDisabledTextColor,
    isEnabled: Boolean = true,
    isRounded: Boolean = true,
    painterStart: Painter? = null,
    anyEnd: @Composable (() -> Any)? = null,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onClick: () -> Unit
) {
    var clickJob by remember { mutableStateOf<Job?>(null) }

    Button(
        onClick = {
            if (clickJob == null) {
                clickJob = coroutineScope.launch {
                    onClick()

                    delay(500)
                    clickJob = null
                }
            }
        },
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(if (isRounded) 16.dp else 0.dp),
        contentPadding = PaddingValues(horizontal = 25.dp, vertical = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor.color,
            contentColor = textColor.color,
            disabledContainerColor = disableBackgroundColor.color,
            disabledContentColor = disableTextColor.color
        )
    ) {
        painterStart?.let {
            Icon(
                painter = it,
                contentDescription = "ic_painter"
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        GearsDealerText(
            text = text,
            type = GearsDealerTextType.Headline16,
            textColor = if (isEnabled) textColor else disableTextColor
        )
        anyEnd?.invoke()
    }
}

@Composable
fun CompoundDealerButton(
    actions: List<CompoundDealerButtonAction>,
    primaryActionIndex: Int,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    if (actions.isEmpty()) return
    val clickJobs = remember { mutableStateListOf<Job?>(null) }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        actions.forEachIndexed { i, compoundButtonAction ->
            val isPrimary = i == primaryActionIndex || actions.size == 1

            if (!isPrimary && !compoundButtonAction.isEnabled) return@forEachIndexed

            val background = if (isPrimary) DealerColor.BlueDarker else DealerColor.White

            val enabledStateTextColor = if (isPrimary) {
                DealerColor.White
            } else DealerColor.Ink60

            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                enabled = compoundButtonAction.isEnabled,
                shape = RectangleShape,
                contentPadding = PaddingValues(horizontal = 11.dp, vertical = 20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    focusedElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = background.color,
                    disabledContainerColor = DealerColor.Ink40.color,
                    contentColor = enabledStateTextColor.color,
                    disabledContentColor = DealerColor.White.color
                ),
                onClick = {
                    if (clickJobs.getOrNull(i) == null) {
                        val newClickJob = coroutineScope.launch {
                            compoundButtonAction.action()

                            delay(500)
                            clickJobs.removeAt(i)
                        }

                        clickJobs.add(i, newClickJob)
                    }
                }
            ) {
                compoundButtonAction.iconPainter?.let { iconPainter ->
                    Icon(
                        painter = iconPainter,
                        contentDescription = compoundButtonAction.label
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                GearsDealerText(
                    text = compoundButtonAction.label,
                    textColor = enabledStateTextColor,
                    type = GearsDealerTextType.Headline16,
                    maxLines = maxLines
                )
            }
        }
    }
}

data class CompoundDealerButtonAction(
    val label: String,
    val isEnabled: Boolean = true,
    val iconPainter: Painter? = null,
    val action: () -> Unit
)
