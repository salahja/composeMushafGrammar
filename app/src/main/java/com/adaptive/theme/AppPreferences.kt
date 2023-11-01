package com.adaptive.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import io.paperdb.Paper

object AppPreferences {

    //region Theme

    const val APP_THEME = "AppTheme"

    fun setTheme(theme: AppTheme) {
        Paper.book().write(APP_THEME, theme)
    }

    fun getTheme(): AppTheme {
        return Paper.book().read(APP_THEME, AppTheme.Blue)!!
    }

    //endregion

}

