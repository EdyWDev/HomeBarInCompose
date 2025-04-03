package com.example.homebarincompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


val lightScheme = lightColorScheme(
    primary = Color(0xFF4E92B5),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE1F1FB),
    onPrimaryContainer = Color(0xFF003B57),

    secondary = Color(0xFF8C9A9F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD3E1E6),
    onSecondaryContainer = Color(0xFF003B47),

    background = Color(0xFFF7F7F7),
    onBackground = Color(0xFF333333),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000)
)

val darkScheme = darkColorScheme(
    primary = Color(0xFF2C4E72),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF163E59),
    onPrimaryContainer = Color(0xFFDAE5EF),

    secondary = Color(0xFF3C4C59),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF25323D),
    onSecondaryContainer = Color(0xFFD0E3EC),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1D1D1D),
    onSurface = Color(0xFFE0E0E0)
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}


