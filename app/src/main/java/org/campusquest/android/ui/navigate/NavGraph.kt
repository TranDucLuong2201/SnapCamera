package org.campusquest.android.ui.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.campusquest.core.presentation.auth.AuthenticationScreen
import org.campusquest.core.presentation.home_graph.dictionary.DictionaryScreen
import org.campusquest.core.presentation.home_graph.HomeScreen
import org.campusquest.core.presentation.home_graph.explore.ExploreScreen
import org.campusquest.core.presentation.home_graph.learning.AILearningScreen
import org.campusquest.core.presentation.onboarding.OnboardingScreen
import org.campusquest.core.presentation.home_graph.profile.ProfileScreen
import org.campusquest.core.presentation.splash.SplashScreen
import org.campusquest.core.utils.Constant.ON_BOARDING_MODEL

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppNavState = remember(navController) { AppNavState(navController) }


fun NavGraphBuilder.setUpNavGraph(
    appNavState: AppNavState,
    modifier: Modifier,
    navController: NavHostController
) {
    composable(Destination.Profile.route) {
        ProfileScreen()
    }
    composable(Destination.Dictionary.route) {
        DictionaryScreen()
    }
    composable(Destination.Explore.route) {
        ExploreScreen()
    }
    composable(Destination.AILearning.route) {
        AILearningScreen()
    }
    composable(Destination.Splash.route) {
        SplashScreen(
            modifier = modifier,
            openAndPopUp = { route, popUp ->
                appNavState.openAndPopUp(route, popUp)
            }
        )
    }
    composable(Destination.OnBoarding.route) {
        OnboardingScreen(
            modifier,
            ON_BOARDING_MODEL,
            clearAndNavigate = { route -> appNavState.clearAndNavigate(route) }
        )
    }
    composable(Destination.Home.route) {
        HomeScreen(
            onNavigateToProfile = {},
            modifier = modifier,
        )
    }
    composable(Destination.Authentication.route) {
        AuthenticationScreen(
            modifier = modifier,
            clearAndNavigate = { route -> appNavState.clearAndNavigate(route) }
        )
    }
}

