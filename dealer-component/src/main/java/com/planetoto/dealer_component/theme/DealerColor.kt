package com.planetoto.dealer_component.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
@JvmInline
value class DealerColor(val color: Color) {
    companion object {
        val Transparent = DealerColor(Color.Transparent)
        val Black = DealerColor(Color.Black)
        val BlackAlpha50 = DealerColor(Black.color.copy(alpha = .5f))
        val White = DealerColor(Color.White)
        val WhiteAlpha80 = DealerColor(White.color.copy(alpha = .8f))
        val WhiteAlpha60 = DealerColor(White.color.copy(alpha = .6f))
        val WhiteAlpha50 = DealerColor(White.color.copy(alpha = .5f))
        val WhiteAlpha25 = DealerColor(White.color.copy(alpha = .25f))
        val WhiteAlpha10 = DealerColor(White.color.copy(alpha = .1f))
        val LightGray = DealerColor(Color(0xFFD4D4D4))
        val DarkGray = DealerColor(Color(0xFFEEEEEE))

        val Monochrome800 = DealerColor(Color(0xFF081724))
        val Monochrome600 = DealerColor(Color(0xFF838B92))

        val Ink100 = DealerColor(Color(0xFF111111))
        val Ink100Alpha30 = DealerColor(Ink100.color.copy(alpha = .3f))
        val Ink80 = DealerColor(Color(0xFF454F5B))
        val Ink60 = DealerColor(Color(0xFF919EAB))
        val Ink60Alpha20 = DealerColor(Ink60.color.copy(alpha = .2f))
        val Ink40 = DealerColor(Color(0xFFC4CDD5))
        val Ink20 = DealerColor(Color(0xFFDFE3E8))
        val Ink10 = DealerColor(Color(0xFFF4F6F8))

        val BlueDarkest = DealerColor(Color(0xFF00193F))
        val BlueDarker = DealerColor(Color(0xFF164183))
        val PlanetBlue = DealerColor(Color(0xFF1B51A4))
        val BlueBackground = DealerColor(Color(0xFFEBF5FA))
        val BlueLight = DealerColor(Color(0xFFEBF5FA))
        val BlueMidNight = DealerColor(Color(0xFF0C316A))

        val RedDarkest = DealerColor(Color(0xFF330202))
        val RedDarker = DealerColor(Color(0xFFBF1D08))
        val PlanetRed = DealerColor(Color(0xFFE22726))
        val RedBackground = DealerColor(Color(0xFFFBEAE5))
        val RedLight = DealerColor(Color(0xFFFBEAE5))

        val AccentBlue = DealerColor(Color(0xFF1B2DE3))
        val AccentRed = DealerColor(Color(0xFFF02929))
        val AccentGreen = DealerColor(Color(0xFF388B1B))
        val AccentGreenAlpha8 = DealerColor(AccentGreen.color.copy(alpha = .08f))
        val AccentYellow = DealerColor(Color(0xFFFABF28))
        val AccentPurple = DealerColor(Color(0xFF8B1B4A))
        val AccentOrange = DealerColor(Color(0xFFD65309))

        val DarkGreen = DealerColor(Color(0xFF19A350))
        val GreenLight = DealerColor(AccentGreen.color.copy(alpha = .1f))

        val WageningenGreen = DealerColor(Color(0xFF26A141))
        val Platinum = DealerColor(Color(0xFFE6E8E9))
        val PrimaryGradient = DealerColor(Color(0xFFF02E2A))
        val SecondaryGradient = DealerColor(Color(0xFF101EBC))

        val TextFieldErrorBackground = DealerColor(Color(0xFFF5F5F5))
        val TextFieldFilledBackground = DealerColor(Color(0xFFFAFAFA))
        val TextFieldFocusedBackground = DealerColor(Color(0xFFEBF5FA))
        val TextFieldFocusedStroke = DealerColor(Color(0xFF091AD6))
        val TextFieldFilledStroke = DealerColor(Color(0xFF888888))
        val TextFieldEmptyStroke = DealerColor(Color(0x66888888))

        val ButtonDisabledTextColor = DealerColor(White.color.copy(alpha = .4f))
        val ButtonDisabledBackground = DealerColor(White.color.copy(alpha = .2f))

        val NotificationBorder = DealerColor(Color(0xFFE6E6E6))
        val ShadowBidDialogTitle = DealerColor(Color(0xFF1E1E1E))
    }

    fun alpha(value: Float): DealerColor = DealerColor(color.copy(alpha = value))
}