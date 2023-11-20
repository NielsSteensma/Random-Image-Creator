package com.randomimagecreator.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RandomImageCreatorTheme(
    content: @Composable () -> Unit
) {
    val colors = if (isSystemInDarkTheme()) {
        darkColors(
            primary = Color(0, 68, 85),
        )
    } else {
        lightColors(
            primary = Color(0, 68, 85),
        )
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        content = content
    )
}