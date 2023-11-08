package com.settings.preference

import AppScaffold
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState
import com.alorma.compose.settings.ui.SettingsList

import com.alorma.compose.settings.ui.SettingsSwitch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable


fun SeetingScreen(navController: NavHostController,
                  darkThemePreference: BooleanPreferenceSettingValueState,
                  dynamicThemePreference: BooleanPreferenceSettingValueState) {
    val state = rememberBooleanSettingState()
    val localContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val enabledState = rememberBooleanSettingState(true)

    AppScaffold(
        enabledState = enabledState,
        navController = navController,
        title = { Text(text = "Switches") },
        snackbarHostState = snackbarHostState,
    ) {


        Column(
            Modifier.fillMaxSize().wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(Modifier.height(100.dp)) {


                val darkmode = rememberPreferenceBooleanSettingState(
                    key = "Dark",
                    defaultValue = false,
                )
                SettingsSwitch(
                    enabled = enabledState.value,
                    state = darkThemePreference,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.SortByAlpha,
                            contentDescription = "Memory switch 1",
                        )
                    },
                    title = { androidx.compose.material3.Text(text = "Dark Mode") },
                    onCheckedChange = {
                        snackbarHostState.showChange(
                            coroutineScope = coroutineScope,
                            key = "Dark",
                            state = darkmode,
                        )
                    },
                )
                Divider()
            }

            Row(Modifier.height(100.dp)) {


                val darkmode = rememberPreferenceBooleanSettingState(
                    key = "switch_2",
                    defaultValue = false,
                )
                // Dynamic theme is not supported on lower API levels.
                if (Build.VERSION.SDK_INT >= 31) {
                    com.settings.SettingsSwitch(
                        state = dynamicThemePreference,
                        title = { Text(text = "Dynamic theme") },
                        subtitle = { Text(text = "Dynamic theme based on wallpaper") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }




            Row(Modifier.height(100.dp)) {



                val preferenceStorage = rememberPreferenceBooleanSettingState(
                    key = "Conjugation",
                    defaultValue = false,
                )
                SettingsSwitch(
                    enabled = enabledState.value,
                    state = preferenceStorage,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.SortByAlpha,
                            contentDescription = "Preferences switch 1",
                        )
                    },
                    title = { androidx.compose.material3.Text(text = "Verb Conjugation") },
                    subtitle = { androidx.compose.material3.Text(text = "Tradional/Column") },
                    onCheckedChange = {
                        snackbarHostState.showChange(
                            coroutineScope = coroutineScope,
                            key = "Conjugation",
                            state = preferenceStorage,
                        )
                    },
                )

                Divider()
            }
            Row(Modifier.height(50.dp)) {


                val showTranslation = rememberPreferenceBooleanSettingState(
                    key = "showtranslation",
                    defaultValue = false,
                )


                SettingsSwitch(
                    enabled = enabledState.value,
                    state = showTranslation,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.SortByAlpha,
                            contentDescription = "Memory switch 1",
                        )
                    },
                    title = { androidx.compose.material3.Text(text = "Show Translation") },
                    onCheckedChange = {
                        snackbarHostState.showChange(
                            coroutineScope = coroutineScope,
                            key = "showtranslation",
                            state = showTranslation,
                        )
                    },
                )
                Divider()
            }
            Row(Modifier.height(50.dp)) {


                val wordbywordState = rememberPreferenceBooleanSettingState(
                    key = "wbw",
                    defaultValue = false,
                )


                SettingsSwitch(
                    enabled = enabledState.value,
                    state = wordbywordState,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.SortByAlpha,
                            contentDescription = "Memory switch 1",
                        )
                    },
                    title = { androidx.compose.material3.Text(text = "Word By Word") },
                    onCheckedChange = {
                        snackbarHostState.showChange(
                            coroutineScope = coroutineScope,
                            key = "wbw",
                            state = wordbywordState,
                        )
                    },
                )
                Divider()
            }
            Row(Modifier.height(100.dp)) {

                val selectTranslation = rememberPreferenceIntSettingState(key = "selecttranslation")

              //  val selectTranslation = rememberPreferenceIntSettingState(key = "selecttranslation")
                SettingsList(
                    enabled = enabledState.value,
                    state = selectTranslation,
                    title = { androidx.compose.material3.Text(text = "Translation") },
                    subtitle = { androidx.compose.material3.Text(text = "Select a Translation") },
                    items = listOf("Sahi International", "Arberry", "Jalalayan"),
                    action = { enabled ->
                        IconButton(
                            enabled = enabled,
                            onClick = { selectTranslation.reset() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    },
                    onItemSelected = { _, text ->
                        Toast.makeText(localContext, "Selected Item: $text", Toast.LENGTH_SHORT)
                            .show()
                    },
                )
                Divider()
            }
            Row(Modifier.height(100.dp)) {



                val wbwchoice = rememberPreferenceIntSettingState(key = "wbwtranslation")
                SettingsList(
                    enabled = enabledState.value,
                    state = wbwchoice,
                    title = { androidx.compose.material3.Text(text = "WBW Translation") },
                    subtitle = { androidx.compose.material3.Text(text = "Select Word By Word Translation") },
                    items = listOf("English", "Urdu", "Indonesian","Bangla"),
                    action = { enabled ->
                        IconButton(
                            enabled = enabled,
                            onClick = { wbwchoice.reset() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    },
                    onItemSelected = { _, text ->
                        Toast.makeText(localContext, "Selected Item: $text", Toast.LENGTH_SHORT)
                            .show()
                    },
                )

                Divider()
            }
            Row(Modifier.height(100.dp)) {


                val ArabicFontChoice = rememberPreferenceIntSettingState(key = "arabicfont")
                SettingsList(
                    enabled = enabledState.value,
                    state = ArabicFontChoice,
                    title = { androidx.compose.material3.Text(text = "Arabic Font Selction") },
                    subtitle = { androidx.compose.material3.Text(text = "Select a fruit") },
                    items = listOf("English", "Urdu", "Indonesian","Bangla"),
                    action = { enabled ->
                        IconButton(
                            enabled = enabled,
                            onClick = { ArabicFontChoice.reset() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    },
                    onItemSelected = { _, text ->
                        Toast.makeText(localContext, "Selected Item: $text", Toast.LENGTH_SHORT)
                            .show()
                    },
                )
            }

            Row(Modifier.height(100.dp)) {



                val themeSelectionChoice = rememberPreferenceIntSettingState(key = "list_pref_1")
                SettingsList(
                    enabled = enabledState.value,
                    state = themeSelectionChoice,
                    title = { androidx.compose.material3.Text(text = "Arabic Font Selction") },
                    subtitle = { androidx.compose.material3.Text(text = "Select a fruit") },
                    items = listOf("English", "Urdu", "Indonesian","Bangla"),
                    action = { enabled ->
                        IconButton(
                            enabled = enabled,
                            onClick = { themeSelectionChoice.reset() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    },
                    onItemSelected = { _, text ->
                        Toast.makeText(localContext, "Selected Item: $text", Toast.LENGTH_SHORT)
                            .show()
                    },
                )
            }

        }
    }
}
private fun SnackbarHostState.showChange(
    coroutineScope: CoroutineScope,
    key: String,
    state: SettingValueState<Boolean>,
) {
    coroutineScope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(message = "[$key]:  ${state.value}")
    }
}