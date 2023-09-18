package com.wize.dashboard.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.wize.dashboard.ui.theme.WizeColor.Background
import com.wize.dashboard.ui.theme.WizeColor.Primary
import com.wize.dashboard.ui.theme.WizeColor.PrimaryDark
import com.wize.dashboard.ui.theme.WizeColor.Secondary
import com.wize.dashboard.ui.theme.WizeColor.Tertiary

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,

    primaryContainer = Background,
    onPrimaryContainer = Background,
    inversePrimary = Background,
    onSecondary = PrimaryDark,
    secondaryContainer = Background,
    onSecondaryContainer = Background,
    onTertiary = Tertiary,
    tertiaryContainer = Background,
    onTertiaryContainer = Background,
    background = Background,
    onBackground = Background,
    surface = Background,
    onSurface = Background,
    surfaceVariant = Background,
    onSurfaceVariant = Background,
    inverseSurface = Background,
    inverseOnSurface = Background,
    outline = Primary
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,

    primaryContainer = Background,
    onPrimaryContainer = Background,
    inversePrimary = Background,
    onSecondary = PrimaryDark,
    secondaryContainer = Background,
    onSecondaryContainer = Background,
    onTertiary = Tertiary,
    tertiaryContainer = Background,
    onTertiaryContainer = Background,
    background = Background,
    onBackground = Background,
    surface = Background,
    onSurface = Background,
    surfaceVariant = Background,
    onSurfaceVariant = Background,
    inverseSurface = Background,
    inverseOnSurface = Background,
    outline = Primary
)

@Composable
fun WizeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = WizeTypography,
        content = content
    )
}