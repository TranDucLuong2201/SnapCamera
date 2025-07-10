package org.campusquest.android.ui.navigate

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
class AppNavState(
    val navController: NavHostController
) {
    fun popUp() {
        navController.navigateUp()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun openAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}