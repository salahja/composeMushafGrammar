package com.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(userId = null, isOnlyDetailScreen = true))
    val uiState = _uiState.asStateFlow()


    fun updateUserId(userIdVal: String?) {
        println("updateUserId")
        _uiState.value = uiState.value.copy(userId = userIdVal)
    }

    fun updateIsOnlyScreen(isOnlyScreen: Boolean) {
        _uiState.value = uiState.value.copy(isOnlyDetailScreen = isOnlyScreen)
    }

    fun updateFullState(userIdVal: String?, isOnlyScreen: Boolean) {
     _uiState.value = HomeUiState(userId = userIdVal, isOnlyDetailScreen = isOnlyScreen)
    }
}

data class HomeUiState(
    var userId: String?,
    var isOnlyDetailScreen: Boolean
)