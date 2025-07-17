package org.campusquest.core.presentation.ui.navigate

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Stable
class AppNavState(
    val navController: NavHostController
) {
    fun popUp() {
        navController.navigateUp()
    }
    fun signOutToSplashScreen() {
        navController.navigate(Destination.Splash.route) {
            popUpTo(0)
        }
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

    fun navigateToBottomTab(route: String) {
        try {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("e.toString()", it) }
            navController.navigate(route) {
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppNavState = remember(navController) { AppNavState(navController) }