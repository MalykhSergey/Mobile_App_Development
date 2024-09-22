package com.example.mobileappdevelopmentomstu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color.Red,
    secondary = Color.Green,
    tertiary = Color.Blue,
    surface = Color(0xFF4D4D4D),
    onSurface = Color(0xFF1C1B1F),
    onBackground = Color(0xFF1C1B1F),
    onTertiary = Color.Green,
    onSecondary = Color.Red,
    onPrimary = Color.Blue,
    background = Color(0xFF4D4D4D)
)

@Composable
fun MobileAppDevelopmentOMSTUTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}