package org.campusquest.core.presentation.home_graph.me.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    fun updatePermission(enabled: Boolean) {
        _settingsState.update { it.copy(permissionEnabled = enabled) }
        // Add logic to handle permission changes (e.g., update app settings)
    }

    fun updatePushNotification(enabled: Boolean) {
        _settingsState.update { it.copy(pushNotificationEnabled = enabled) }
        // Add logic to handle push notification changes
    }

    fun updateDarkMode(enabled: Boolean) {
        _settingsState.update { it.copy(darkModeEnabled = enabled) }
        // Add logic to toggle dark mode (e.g., update theme)
    }
}