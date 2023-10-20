package com.skyyo.expandablelist.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.codelab.basics.ui.theme.md_theme_dark_background
import com.codelab.basics.ui.theme.md_theme_dark_error
import com.codelab.basics.ui.theme.md_theme_dark_errorContainer
import com.codelab.basics.ui.theme.md_theme_dark_inverseOnSurface
import com.codelab.basics.ui.theme.md_theme_dark_inversePrimary
import com.codelab.basics.ui.theme.md_theme_dark_inverseSurface
import com.codelab.basics.ui.theme.md_theme_dark_onBackground
import com.codelab.basics.ui.theme.md_theme_dark_onError
import com.codelab.basics.ui.theme.md_theme_dark_onErrorContainer
import com.codelab.basics.ui.theme.md_theme_dark_onPrimary
import com.codelab.basics.ui.theme.md_theme_dark_onPrimaryContainer
import com.codelab.basics.ui.theme.md_theme_dark_onSecondary
import com.codelab.basics.ui.theme.md_theme_dark_onSecondaryContainer
import com.codelab.basics.ui.theme.md_theme_dark_onSurface
import com.codelab.basics.ui.theme.md_theme_dark_onSurfaceVariant
import com.codelab.basics.ui.theme.md_theme_dark_onTertiary
import com.codelab.basics.ui.theme.md_theme_dark_onTertiaryContainer
import com.codelab.basics.ui.theme.md_theme_dark_outline
import com.codelab.basics.ui.theme.md_theme_dark_outlineVariant
import com.codelab.basics.ui.theme.md_theme_dark_primary
import com.codelab.basics.ui.theme.md_theme_dark_primaryContainer
import com.codelab.basics.ui.theme.md_theme_dark_scrim
import com.codelab.basics.ui.theme.md_theme_dark_secondary
import com.codelab.basics.ui.theme.md_theme_dark_secondaryContainer
import com.codelab.basics.ui.theme.md_theme_dark_surface
import com.codelab.basics.ui.theme.md_theme_dark_surfaceTint
import com.codelab.basics.ui.theme.md_theme_dark_surfaceVariant
import com.codelab.basics.ui.theme.md_theme_dark_tertiary
import com.codelab.basics.ui.theme.md_theme_dark_tertiaryContainer
import com.codelab.basics.ui.theme.md_theme_light_background
import com.codelab.basics.ui.theme.md_theme_light_error
import com.codelab.basics.ui.theme.md_theme_light_errorContainer
import com.codelab.basics.ui.theme.md_theme_light_inverseOnSurface
import com.codelab.basics.ui.theme.md_theme_light_inversePrimary
import com.codelab.basics.ui.theme.md_theme_light_inverseSurface
import com.codelab.basics.ui.theme.md_theme_light_onBackground
import com.codelab.basics.ui.theme.md_theme_light_onError
import com.codelab.basics.ui.theme.md_theme_light_onErrorContainer
import com.codelab.basics.ui.theme.md_theme_light_onPrimary
import com.codelab.basics.ui.theme.md_theme_light_onPrimaryContainer
import com.codelab.basics.ui.theme.md_theme_light_onSecondary
import com.codelab.basics.ui.theme.md_theme_light_onSecondaryContainer
import com.codelab.basics.ui.theme.md_theme_light_onSurface
import com.codelab.basics.ui.theme.md_theme_light_onSurfaceVariant
import com.codelab.basics.ui.theme.md_theme_light_onTertiary
import com.codelab.basics.ui.theme.md_theme_light_onTertiaryContainer
import com.codelab.basics.ui.theme.md_theme_light_outline
import com.codelab.basics.ui.theme.md_theme_light_outlineVariant
import com.codelab.basics.ui.theme.md_theme_light_primary
import com.codelab.basics.ui.theme.md_theme_light_primaryContainer
import com.codelab.basics.ui.theme.md_theme_light_scrim
import com.codelab.basics.ui.theme.md_theme_light_secondary
import com.codelab.basics.ui.theme.md_theme_light_secondaryContainer
import com.codelab.basics.ui.theme.md_theme_light_surface
import com.codelab.basics.ui.theme.md_theme_light_surfaceTint
import com.codelab.basics.ui.theme.md_theme_light_surfaceVariant
import com.codelab.basics.ui.theme.md_theme_light_tertiary
import com.codelab.basics.ui.theme.md_theme_light_tertiaryContainer

/*
private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)
*/

/*
private val LightBrownColors= lightColorScheme(



 val md_theme_light_primary = Color(0xFF6A5F00)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFF7E467)
val md_theme_light_onPrimaryContainer = Color(0xFF201C00)
val md_theme_light_secondary = Color(0xFF645F41)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFEBE3BD)
val md_theme_light_onSecondaryContainer = Color(0xFF1F1C05)
val md_theme_light_tertiary = Color(0xFF416651)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFC3ECD1)
val md_theme_light_onTertiaryContainer = Color(0xFF002112)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1D1C16)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1D1C16)
val md_theme_light_surfaceVariant = Color(0xFFE8E2D0)
val md_theme_light_onSurfaceVariant = Color(0xFF4A4739)
val md_theme_light_outline = Color(0xFF7B7768)
val md_theme_light_inverseOnSurface = Color(0xFFF5F0E7)
val md_theme_light_inverseSurface = Color(0xFF32302A)
val md_theme_light_inversePrimary = Color(0xFFDAC84E)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF6A5F00)
val md_theme_light_outlineVariant = Color(0xFFCCC6B5)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFDAC84E)
val md_theme_dark_onPrimary = Color(0xFF373100)
val md_theme_dark_primaryContainer = Color(0xFF504700)
val md_theme_dark_onPrimaryContainer = Color(0xFFF7E467)
val md_theme_dark_secondary = Color(0xFFCFC7A2)
val md_theme_dark_onSecondary = Color(0xFF353117)
val md_theme_dark_secondaryContainer = Color(0xFF4C472B)
val md_theme_dark_onSecondaryContainer = Color(0xFFEBE3BD)
val md_theme_dark_tertiary = Color(0xFFA7D0B6)
val md_theme_dark_onTertiary = Color(0xFF113725)
val md_theme_dark_tertiaryContainer = Color(0xFF294E3A)
val md_theme_dark_onTertiaryContainer = Color(0xFFC3ECD1)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1D1C16)
val md_theme_dark_onBackground = Color(0xFFE7E2D9)
val md_theme_dark_surface = Color(0xFF1D1C16)
val md_theme_dark_onSurface = Color(0xFFE7E2D9)
val md_theme_dark_surfaceVariant = Color(0xFF4A4739)
val md_theme_dark_onSurfaceVariant = Color(0xFFCCC6B5)
val md_theme_dark_outline = Color(0xFF959181)
val md_theme_dark_inverseOnSurface = Color(0xFF1D1C16)
val md_theme_dark_inverseSurface = Color(0xFFE7E2D9)
val md_theme_dark_inversePrimary = Color(0xFF6A5F00)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFDAC84E)
val md_theme_dark_outlineVariant = Color(0xFF4A4739)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFFFFF8E1)
)
*/


private val LightThemeColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkThemeColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

/*
@Composable
fun JetRedditTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (JetRedditThemeSettings.isInDarkTheme.value) DarkThemeColors else LightThemeColors,
        content = content
    )
}


object JetRedditThemeSettings {
    var isInDarkTheme: MutableState<Boolean> = mutableStateOf(false)
}

*/


@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val isDarkThemeEnabled = isSystemInDarkTheme() || AppThemeSettings.isDarkThemeEnabled
    val colors = if (isDarkThemeEnabled) DarkThemeColors else LightThemeColors

    MaterialTheme(colorScheme = colors, content = content)
}

/**
 * Allows changing between light and a dark theme from the app's settings.
 */
object AppThemeSettings {
    var isDarkThemeEnabled by mutableStateOf(false)
}
