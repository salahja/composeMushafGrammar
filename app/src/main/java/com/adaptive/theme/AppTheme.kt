package com.adaptive.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import colorBlack
import colorBlue
import colorBlue1
import colorGreen
import colorRed
import colorWhite
import com.google.accompanist.systemuicontroller.rememberSystemUiController


enum class AppTheme {
    Blue, Red, Green
}

private val BlueColorScheme = lightColorScheme(
    primary = colorBlue,
    secondary = colorBlue1,
    background = colorWhite,
    onPrimary = colorWhite,
    onSecondary = colorWhite,
    onBackground = colorBlack
)


private val RedColorScheme = lightColorScheme(
    primary = colorRed,
    secondary = colorBlue1,
    background = colorWhite,
    onPrimary = colorWhite,
    onSecondary = colorWhite,
    onBackground = colorBlack
)


private val GreenColorScheme = lightColorScheme(
    primary = colorGreen,
    secondary = colorBlue1,
    background = colorWhite,
    onPrimary = colorWhite,
    onSecondary = colorWhite,
    onBackground = colorBlack
)

@Composable
fun MyApplicationTheme(
    appTheme: AppTheme,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when (appTheme) {
        AppTheme.Blue -> {
            BlueColorScheme
        }
        AppTheme.Red -> {
            RedColorScheme
        }
        AppTheme.Green -> {
            GreenColorScheme
        }
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colorScheme.primary,
            darkIcons = false,
        )
    }
    MaterialTheme(
        colorScheme = colorScheme,  content = content
    )
}