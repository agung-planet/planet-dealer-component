package com.planetoto.dealer_component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.planetoto.dealer_component.theme.DealerColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class GearsDealerTextType(
    val textSize: TextUnit,
    val lineHeight: TextUnit,
    val fontWeight: FontWeight? = null
) {
    object Headline28 : GearsDealerTextType(28.sp, 33.sp)
    object Headline20 : GearsDealerTextType(20.sp, 25.sp)
    object Headline18 : GearsDealerTextType(18.sp, 25.sp)
    object Headline16 : GearsDealerTextType(16.sp, 21.sp)
    object Headline14 : GearsDealerTextType(14.sp, 21.sp)
    object Subtitle16 : GearsDealerTextType(16.sp, 20.sp)
    object Subtitle12 : GearsDealerTextType(12.sp, 14.sp)
    object CaptionUppercase : GearsDealerTextType(11.sp, 21.sp)
    object CaptionLabel : GearsDealerTextType(11.sp, 13.sp)
    object CaptionLabel9 : GearsDealerTextType(9.sp, 9.sp)
    object Text14 : GearsDealerTextType(14.sp, 24.sp)
    object Text14Bold : GearsDealerTextType(14.sp, 24.sp)
    object TextSmallText : GearsDealerTextType(12.sp, 22.sp)
    object NumberInputField : GearsDealerTextType(20.sp, 24.sp)
}

@Composable
fun GearsDealerText(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit? = null,
    textColor: DealerColor = DealerColor.Ink100,
    type: GearsDealerTextType = GearsDealerTextType.Subtitle16,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit? = null,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    fontStyle: FontStyle = FontStyle.Normal,
    textStyle: TextStyle = TextStyle.Default,
) {
    val fontResId = when (type) {
        GearsDealerTextType.CaptionLabel, GearsDealerTextType.CaptionLabel9 -> R.font.inter_medium

        GearsDealerTextType.CaptionUppercase,
        GearsDealerTextType.TextSmallText,
        GearsDealerTextType.Text14 -> R.font.inter_regular

        GearsDealerTextType.Subtitle12,
        GearsDealerTextType.Subtitle16,
        GearsDealerTextType.Text14Bold,
        GearsDealerTextType.NumberInputField -> {
            R.font.inter_semi_bold
        }

        else -> R.font.effra
    }

    Text(
        modifier = modifier,
        text = text,
        color = textColor.color,
        fontSize = textSize ?: type.textSize,
        lineHeight = lineHeight ?: type.lineHeight,
        fontWeight = fontWeight ?: type.fontWeight,
        fontFamily = FontFamily(Font(resId = fontResId)),
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontStyle = fontStyle,
        style = textStyle,
    )
}

@Composable
fun GearsDealerText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textSize: TextUnit? = null,
    textColor: DealerColor = DealerColor.Ink100,
    type: GearsDealerTextType = GearsDealerTextType.Subtitle16,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit? = null,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    fontStyle: FontStyle = FontStyle.Normal,
    textStyle: TextStyle = TextStyle.Default,
    inlineContent: Map<String, InlineTextContent> = mapOf()
) {
    val fontResId = when (type) {
        GearsDealerTextType.CaptionLabel, GearsDealerTextType.CaptionLabel9 -> R.font.inter_medium

        GearsDealerTextType.CaptionUppercase,
        GearsDealerTextType.TextSmallText,
        GearsDealerTextType.Text14 -> R.font.inter_regular

        GearsDealerTextType.Subtitle12,
        GearsDealerTextType.Subtitle16,
        GearsDealerTextType.Text14Bold,
        GearsDealerTextType.NumberInputField -> {
            R.font.inter_semi_bold
        }

        else -> R.font.effra
    }

    Text(
        modifier = modifier,
        text = text,
        color = textColor.color,
        fontSize = textSize ?: type.textSize,
        lineHeight = lineHeight ?: type.lineHeight,
        fontWeight = fontWeight ?: type.fontWeight,
        fontFamily = FontFamily(Font(resId = fontResId)),
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontStyle = fontStyle,
        inlineContent = inlineContent,
        style = textStyle,
    )
}

@Composable
fun GearsDealerClickableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    type: GearsDealerTextType = GearsDealerTextType.Subtitle16,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (Int) -> Unit
) {
    val fontResId = when (type) {
        GearsDealerTextType.CaptionLabel, GearsDealerTextType.CaptionLabel9 -> R.font.inter_medium

        GearsDealerTextType.CaptionUppercase,
        GearsDealerTextType.TextSmallText,
        GearsDealerTextType.Text14 -> R.font.inter_regular

        GearsDealerTextType.Subtitle12,
        GearsDealerTextType.Subtitle16,
        GearsDealerTextType.Text14Bold,
        GearsDealerTextType.NumberInputField -> {
            R.font.inter_semi_bold
        }

        else -> R.font.effra
    }

    val coroutineScope = rememberCoroutineScope()
    var clickableJob by remember { mutableStateOf<Job?>(null) }

    ClickableText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = FontFamily(Font(resId = fontResId)),
            fontSize = type.textSize,
            fontWeight = type.fontWeight,
            lineHeight = type.lineHeight
        ),
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        onClick = { offset ->
            if (clickableJob == null) {
                clickableJob = coroutineScope.launch {
                    onClick(offset)

                    delay(500)
                    clickableJob = null
                }
            }
        }
    )
}