package org.android.snap.snapcamera.presentation.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.android.snap.snapcamera.presentation.auth.login.LoginScreen
import org.android.snap.snapcamera.presentation.auth.onboarding.OnboardingScreen
import org.android.snap.snapcamera.presentation.auth.splash.SplashScreen
import org.android.snap.snapcamera.presentation.ui.screens.CameraScreen
import org.android.snap.snapcamera.presentation.ui.screens.CurrentSubjectScreen
import org.android.snap.snapcamera.presentation.ui.screens.HomeScreen
import org.android.snap.snapcamera.presentation.ui.screens.ImageDetailScreen
import org.android.snap.snapcamera.presentation.ui.screens.ImagesScreen
import org.android.snap.snapcamera.presentation.ui.screens.ScheduleScreen
import org.android.snap.snapcamera.presentation.ui.screens.SettingsScreen
import org.android.snap.snapcamera.presentation.ui.screens.StudySessionScreen
import org.android.snap.snapcamera.presentation.ui.screens.SubjectDetailScreen
import org.android.snap.snapcamera.presentation.ui.screens.SubjectsScreen

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Composable
fun rememberAppState(
	navController: NavHostController = rememberNavController(),
): AppNavState = remember(navController) { AppNavState(navController) }

@Composable
fun AppNavGraph(
	navController: NavHostController,
	startDestination: String = Destination.Splash.route,
) {
	NavHost(
		navController = navController,
		startDestination = startDestination,
	) {
		// Splash Screen
		composable(Destination.Splash.route) {
			SplashScreen(
				onNavigateToOnboarding = {
					navController.navigate(Destination.Onboarding.route) {
						popUpTo(Destination.Splash.route) { inclusive = true }
					}
				},
				onNavigateToHome = {
					navController.navigate(Destination.Home.route) {
						popUpTo(Destination.Splash.route) { inclusive = true }
					}
				},
			)
		}

		// Onboarding Screen
		composable(Destination.Onboarding.route) {
			OnboardingScreen(
				onNavigateToLogin = {
					navController.navigate(Destination.Login.route) {
						popUpTo(Destination.Onboarding.route) { inclusive = true }
					}
				},
			)
		}

		// Login Screen
		composable(Destination.Login.route) {
			LoginScreen(
				onNavigateToHome = {
					navController.navigate(Destination.Home.route) {
						popUpTo(Destination.Login.route) { inclusive = true }
					}
				},
			)
		}

		// Home Screen
		composable(Destination.Home.route) {
			HomeScreen(
				onNavigateToCurrentSubject = {
					navController.navigate(Destination.CurrentSubject.route)
				},
				onNavigateToStudySession = {
					navController.navigate(Destination.StudySession.route)
				},
				onNavigateToSubjects = {
					navController.navigate(Destination.Subjects.route)
				},
				onNavigateToSchedule = {
					navController.navigate(Destination.Schedule.route)
				},
				onNavigateToImages = {
					navController.navigate(Destination.Images.route)
				},
				onNavigateToSettings = {
					navController.navigate(Destination.Settings.route)
				},
			)
		}

		// Current Subject Screen
		composable(Destination.CurrentSubject.route) {
			CurrentSubjectScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
			)
		}

		// Study Session Screen
		composable(Destination.StudySession.route) {
			StudySessionScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
				onNavigateToCamera = {
					navController.navigate(Destination.Camera.route)
				},
			)
		}

		// Subjects Screen
		composable(Destination.Subjects.route) {
			SubjectsScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
				onNavigateToSubjectDetail = { subjectId ->
					navController.navigate(Destination.SubjectDetail.createRoute(subjectId))
				},
			)
		}

		// Schedule Screen
		composable(Destination.Schedule.route) {
			ScheduleScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
			)
		}

		// Images Screen
		composable(Destination.Images.route) {
			ImagesScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
				onNavigateToImageDetail = { imageId ->
					navController.navigate(Destination.ImageDetail.createRoute(imageId))
				},
			)
		}

		// Settings Screen
		composable(Destination.Settings.route) {
			SettingsScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
			)
		}

		// Camera Screen
		composable(Destination.Camera.route) {
			CameraScreen(
				onNavigateBack = {
					navController.popBackStack()
				},
			)
		}

		// Image Detail Screen
		composable(
			route = Destination.ImageDetail.route,
			arguments = listOf(
				navArgument("imageId") { type = NavType.StringType },
			),
		) { backStackEntry ->
			val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
			ImageDetailScreen(
				imageId = imageId,
				onNavigateBack = {
					navController.popBackStack()
				},
			)
		}

		// Subject Detail Screen
		composable(
			route = Destination.SubjectDetail.route,
			arguments = listOf(
				navArgument("subjectId") { type = NavType.StringType },
			),
		) { backStackEntry ->
			val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
			SubjectDetailScreen(
				subjectId = subjectId,
				onNavigateBack = {
					navController.popBackStack()
				},
			)
		}
	}
}
