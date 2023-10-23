package com.planetoto.dealer_component.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.Locale

class ThousandSeparatorVisualTransformation : VisualTransformation {
    private val separator = '.'
    private val formatter = NumberFormat.getInstance(Locale("id"))
    private var currentValue = ""

    private val mapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val separatorCount = getOriginalSeparatorCount(currentValue, offset)
            return offset + separatorCount
        }

        private fun getOriginalSeparatorCount(value: String, offset: Int): Int {
            val formattedPrice = toCurrencyFormat(value)
            val totalCount = formattedPrice.count { it == separator }

            val afterOffset = value.substring(offset).ifEmpty { "0" }
            val subCount = toCurrencyFormat(afterOffset, forCountingSeparators = true)
                .count { it == separator }

            return totalCount - subCount
        }

        override fun transformedToOriginal(offset: Int): Int {
            val separatorCount =
                getTransformedSeparatorCount(toCurrencyFormat(currentValue), offset)
            return offset - separatorCount
        }

        private fun getTransformedSeparatorCount(text: String, offset: Int): Int {
            return text.substring(0, offset).count { it == separator }
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        currentValue = text.text
        return TransformedText(AnnotatedString(toCurrencyFormat(currentValue)), mapping)
    }

    private fun toCurrencyFormat(text: String, forCountingSeparators: Boolean = false): String =
        when {
            // if all is "0", can not return correct value for counting separators
            forCountingSeparators && text.all { it == '0' } -> {
                val dummyStr = text.mapIndexed { index, _ -> "${index + 1}" }
                    .joinToString(separator = "")
                formatter.format(dummyStr.toLong())
            }

            text.isNotBlank() -> formatter.format(text.toLong())
            else -> ""
        }
}