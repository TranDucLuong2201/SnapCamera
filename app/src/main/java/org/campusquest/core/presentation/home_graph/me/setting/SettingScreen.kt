package org.campusquest.core.presentation.home_graph.me.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.campusquest.core.presentation.ui.components.SmallTopBar
import org.campusquest.core.presentation.ui.components.TopBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsState by viewModel.settingsState.collectAsState()

    Scaffold(
        topBar = {
            SmallTopBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = listOf(
                    TopBarAction(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        onClick = onBackClick
                    )
                ),
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Permission Toggle
            SettingsItemWithToggle(
                title = "Permission",
                isChecked = settingsState.permissionEnabled,
                onCheckedChange = { viewModel.updatePermission(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Push Notification Toggle
            SettingsItemWithToggle(
                title = "Push Notification",
                isChecked = settingsState.pushNotificationEnabled,
                onCheckedChange = { viewModel.updatePushNotification(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dark Mood Toggle
            SettingsItemWithToggle(
                title = "Dark Mood",
                isChecked = settingsState.darkModeEnabled,
                onCheckedChange = { viewModel.updateDarkMode(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Security
            SettingsItemWithArrow(
                title = "Security",
                onClick = { openAndPopUp("security_route", "settings_route") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Help
            SettingsItemWithArrow(
                title = "Help",
                onClick = { openAndPopUp("help_route", "settings_route") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Language
            SettingsItemWithArrow(
                title = "Language",
                onClick = { openAndPopUp("language_route", "settings_route") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // About Application
            SettingsItemWithArrow(
                title = "About Application",
                onClick = { openAndPopUp("about_route", "settings_route") }
            )
        }
    }
}

@Composable
fun SettingsItemWithToggle(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onCheckedChange(!isChecked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}

@Composable
fun SettingsItemWithArrow(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Navigate",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}

data class SettingsState(
    val permissionEnabled: Boolean = true,
    val pushNotificationEnabled: Boolean = false,
    val darkModeEnabled: Boolean = false
)

