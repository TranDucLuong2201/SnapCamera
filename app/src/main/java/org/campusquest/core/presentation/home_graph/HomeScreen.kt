package org.campusquest.core.presentation.home_graph

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.campusquest.android.ui.components.ModernBottomNavigation
import org.campusquest.android.ui.components.WordFlowTopBar
import org.campusquest.android.ui.navigate.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(Destination.Dictionary.route) }
    val homeNavController = rememberNavController()

    Scaffold(
        topBar = {
            WordFlowTopBar(
                onProfileClick = {  },
                onSettingsClick = { /* Handle settings */ }
            )
        },
        bottomBar = {
            ModernBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    homeNavController.navigate(tab) {
                        // Clear back stack when switching tabs
                        popUpTo(homeNavController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

    }
}


