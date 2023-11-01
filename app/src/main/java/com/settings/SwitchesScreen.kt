package com.settings

import AppScaffold
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSetSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState
import com.alorma.compose.settings.ui.SettingsListDropdown
import com.alorma.compose.settings.ui.SettingsListMultiSelect

import com.alorma.compose.settings.ui.SettingsSwitch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable

fun SwitchesScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val enabledState = rememberBooleanSettingState(true)

    AppScaffold(
        enabledState = enabledState,
        navController = navController,
        title = { Text(text = "Switches") },
        snackbarHostState = snackbarHostState,
    ) {
        val memoryStorage = rememberBooleanSettingState(defaultValue = false)
        SettingsSwitch(
            enabled = enabledState.value,
            state = memoryStorage,
            icon = {
                Icon(
                    imageVector = Icons.Default.SortByAlpha,
                    contentDescription = "Memory switch 1",
                )
            },
            title = { Text(text = "Memory") },
            onCheckedChange = {
                snackbarHostState.showChange(
                    coroutineScope = coroutineScope,
                    key = "Memory",
                    state = memoryStorage,
                )
            },
        )
        Divider()
        val preferenceStorage = rememberPreferenceBooleanSettingState(
            key = "switch_2",
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
            title = { Text(text = "Preferences") },
            onCheckedChange = {
                snackbarHostState.showChange(
                    coroutineScope = coroutineScope,
                    key = "Preferences",
                    state = preferenceStorage,
                )
            },
        )
    }



    AppScaffold(
        enabledState = enabledState,
        navController = navController,
        title = { Text(text = "List") },
    ) {
        val singleChoiceState = rememberPreferenceIntSettingState(key = "list_pref_1")
        com.alorma.compose.settings.ui.SettingsList(
            enabled = enabledState.value,
            state = singleChoiceState,
            title = { Text(text = "Single choice") },
            subtitle = { Text(text = "Select a fruit") },
            items = listOf("Banana", "Kiwi", "Pineapple"),
            action = { enabled ->
                IconButton(
                    enabled = enabled,
                    onClick = { singleChoiceState.reset() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                    )
                }
            },
            onItemSelected = { _, text ->
               // Toast.makeText(localContext, "Selected Item: $text", Toast.LENGTH_SHORT).show()
            },
        )
        Divider()
        val multiChoiceState =
            rememberPreferenceIntSetSettingState(key = "list_pref_2", defaultValue = setOf(1, 2))
        SettingsListMultiSelect(
            enabled = enabledState.value,
            state = multiChoiceState,
            title = { Text(text = "Multi choice") },
            subtitle = { Text(text = "Select multiple fruits") },
            items = listOf("Banana", "Kiwi", "Pineapple"),
            action = { enabled ->
                IconButton(
                    enabled = enabled,
                    onClick = { multiChoiceState.reset() },

                    ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                    )
                }
            },
            confirmButton = "Select",
       /*     onItemsSelected = { items ->
                Toast.makeText(
                //    localContext,
                    "Selected Item: ${items.joinToString(", ")}",
                    Toast.LENGTH_SHORT,
                ).show()
            },*/
        )
        Divider()
        val dropdownChoiceState =
            rememberPreferenceIntSettingState(key = "dropdown_list_pref_1", defaultValue = 0)
        SettingsListDropdown(
            enabled = enabledState.value,
            state = dropdownChoiceState,
            title = { Text(text = "Dropdown choice") },
            subtitle = { Text(text = "Select a single fruit") },
            items = listOf("Banana", "Kiwi", "Pineapple"),
            onItemSelected = { _, text ->
           //     Toast.makeText(localContext, "Selected Item: $text", Toast.LENGTH_SHORT).show()
            },
        )
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
