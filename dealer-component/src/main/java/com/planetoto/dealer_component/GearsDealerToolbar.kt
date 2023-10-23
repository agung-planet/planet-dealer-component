package com.planetoto.dealer_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.planetoto.dealer_component.theme.DealerColor
import com.planetoto.dealer_component.util.getStatusBarHeight

@Composable
fun BasicDealerToolbar(
    title: String,
    isGradientBackground: Boolean = true,
    backgroundColor: DealerColor? = null,
    showNavigateUp: Boolean = false,
    navigateUpIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "up",
            tint = DealerColor.White.color
        )
    },
    onNavigateUp: (() -> Unit)? = null
) {
    if (!isGradientBackground && backgroundColor == null) {
        throw IllegalArgumentException("must set isGradientBackground = true or backgroundColor!")
    }

    Box {
        if (isGradientBackground) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp + getStatusBarHeight()),
                painter = painterResource(id = R.drawable.bg_header_gradient),
                contentDescription = "bg_header_gradient",
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth().let {
                    if (isGradientBackground) {
                        it.background(Color.Transparent)
                    } else {
                        it.background(backgroundColor!!.color)
                    }
                },
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            Row(
                modifier = Modifier
                    .height(60.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showNavigateUp) {
                    if (onNavigateUp == null) throw IllegalArgumentException("must implement onNavigateUp!")
                    IconButton(onClick = onNavigateUp, content = navigateUpIcon)
                } else {
                    Spacer(modifier = Modifier.width(12.dp))
                }

                GearsDealerText(
                    text = title,
                    textColor = DealerColor.White,
                    type = GearsDealerTextType.Subtitle16
                )
            }
        }
    }
}
