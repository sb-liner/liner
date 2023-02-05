package com.liner.liner

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LinerTheme = lightColors(
    primary = Color.Magenta,
    primaryVariant = Color.Magenta,
    secondary = Color.Magenta,
    background = Color.White
)

@Composable
fun LinerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) LinerTheme else LinerTheme
    MaterialTheme(
        colors = colors,
        content = content
    )
}