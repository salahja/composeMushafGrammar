package com.settings



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.example.ui.Navigation

import com.alorma.compose.settings.example.ui.theme.ComposeSettingsTheme
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.settings.preference.SeetingScreen

class SettingAct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkThemePreference = rememberPreferenceBooleanSettingState(
                key = "darkThemePreference",
                defaultValue = true,
            )

            val dynamicThemePreference = rememberPreferenceBooleanSettingState(
                key = "dynamicThemePreference",
                defaultValue = true,
            )

            val navController = rememberNavController()
            ComposeSettingsTheme(
                darkThemePreference = darkThemePreference.value,
                dynamicThemePreference = dynamicThemePreference.value,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Navigation.NAV_TOP_SETTINGS.first,
                    ) {

                        composable(Navigation.NAV_TOP_SETTINGS.first) {
                            SeetingScreen(navController)
                         //   ListScreen(navController = navController)
                        }

                       /* composable(Navigation.NAV_SETTINGS.first) {
                            AppSettingsScreen(
                                navController = navController,
                                darkThemePreference = darkThemePreference,
                                dynamicThemePreference = dynamicThemePreference,
                            )
                        }*/
                    }
                }
            }
        }
    }
}
