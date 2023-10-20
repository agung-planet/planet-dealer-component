package com.planetoto.dealer_component.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberSeparatorVisualTransformation : VisualTransformation {
    private val separator = '-'
    private var value = ""

    private val mapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val separatorCount = getOriginalSeparatorCount(value, offset)
            return offset + separatorCount
        }

        private fun getOriginalSeparatorCount(value: String, offset: Int): Int {
            val formattedString = toPhoneNumberFormat(value)
            val totalCount = formattedString.count { it == separator }

            val afterOffset = value.substring(offset)
            val subCount = toPhoneNumberFormat(afterOffset).count { it == separator }

            return totalCount - subCount
        }

        override fun transformedToOriginal(offset: Int): Int {
            val separatorCount = getTransformedSeparatorCount(toPhoneNumberFormat(value), offset)
            return offset - separatorCount
        }

        private fun getTransformedSeparatorCount(text: String, offset: Int): Int {
            return text.substring(0, offset).count { it == separator }
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        value = text.text
        return TransformedText(
            AnnotatedString(toPhoneNumberFormat(text.text)),
            mapping
        )
    }

    private fun toPhoneNumberFormat(text: String): String {
        val head = text.take(3)
        val list = text.drop(3).chunked(4)
        return mutableListOf<String>().apply {
            add(head)
            addAll(list)
        }.joinToString("-")
    }
}