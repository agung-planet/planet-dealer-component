package com.planetoto.dealer_component.util

import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.HtmlCompat

@Composable
fun String.fromHtmlToText(
    hyperlinkStyle: TextStyle = TextStyle.Default,
): AnnotatedString {
    val spanned = remember(this) {
        HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY, null, null)
    }

    val annotatedText = remember(spanned, hyperlinkStyle) {
        buildAnnotatedString {
            append(spanned.toString())

            spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
                val startIndex = spanned.getSpanStart(span)
                val endIndex = spanned.getSpanEnd(span)
                when (span) {
                    is StyleSpan -> {
                        span.toSpanStyle()?.let {
                            addStyle(style = it, start = startIndex, end = endIndex)
                        }
                    }

                    is UnderlineSpan -> {
                        addStyle(
                            SpanStyle(textDecoration = TextDecoration.Underline),
                            start = startIndex,
                            end = endIndex
                        )
                    }

                    is URLSpan -> {
                        addStyle(
                            style = hyperlinkStyle.toSpanStyle(),
                            start = startIndex,
                            end = endIndex
                        )
                        addStringAnnotation(
                            tag = Tag.Hyperlink.name,
                            annotation = span.url,
                            start = startIndex,
                            end = endIndex
                        )
                    }

                    is ForegroundColorSpan -> addStyle(
                        SpanStyle(color = Color(span.foregroundColor)),
                        startIndex,
                        endIndex
                    )
                }
            }
        }
    }

    return annotatedText
}


private fun StyleSpan.toSpanStyle(): SpanStyle? {
    return when (style) {
        Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
        Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
        Typeface.BOLD_ITALIC -> SpanStyle(
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        else -> null
    }
}

private enum class Tag {
    Hyperlink
}