package org.android.snap.snapcamera.presentation.ui.nav

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Stable
class AppNavState(
	val navController: NavHostController,
) {
	fun popUp() = navController.navigateUp()

	fun navigate(route: String) = navController.navigate(route)

	fun navigateAndPopUp(route: String, popUp: String) = navController.navigate(route) {
		launchSingleTop = true
		popUpTo(popUp) { inclusive = true }
	}
}
