package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val OrbitLightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    inversePrimary = InversePrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = Surface,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceTint = SurfaceTint,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    outline = Outline,
    outlineVariant = OutlineVariant,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer
)

private val OrbitDarkColorScheme = darkColorScheme(
    primary = InversePrimary,
    onPrimary = Primary,
    primaryContainer = Primary,
    onPrimaryContainer = PrimaryFixed,
    secondary = SecondaryFixedDim,
    onSecondary = OnSecondaryFixed,
    secondaryContainer = Secondary,
    onSecondaryContainer = SecondaryFixed,
    tertiary = TertiaryFixed,
    onTertiary = OnTertiaryFixed,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = TertiaryFixedDim,
    background = InverseSurface,
    onBackground = InverseOnSurface,
    surface = InverseSurface,
    onSurface = InverseOnSurface,
    surfaceVariant = OnSurfaceVariant,
    onSurfaceVariant = SurfaceContainerHighest,
    outline = OutlineVariant,
    outlineVariant = Outline,
    error = ErrorContainer,
    onError = OnErrorContainer
)

@Composable
fun OrbitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve bespoke Orbit brand colors
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> OrbitDarkColorScheme
        else -> OrbitLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    OrbitTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}

