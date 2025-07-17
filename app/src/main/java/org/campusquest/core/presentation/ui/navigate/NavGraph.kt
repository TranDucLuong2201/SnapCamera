package org.campusquest.core.presentation.ui.navigate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.campusquest.core.presentation.home_graph.HomeScaffold
import org.campusquest.core.presentation.home_graph.gallery.GalleryScreen
import org.campusquest.core.presentation.home_graph.home.HomeScreen
import org.campusquest.core.presentation.home_graph.me.ProfileScreen
import org.campusquest.core.presentation.home_graph.me.setting.SettingsScreen
import org.campusquest.core.presentation.home_graph.schedule.ScheduleScreen

fun NavGraphBuilder.setUpNavGraph(appNavState: AppNavState) {
    navigation(
        startDestination = Destination.Home.route,
        route = Destination.SetUp.route
    ) {
        homeGraph(appNavState)
        scheduleGraph(appNavState)
        galleryGraph(appNavState)
        profileGraph(appNavState)
    }
}

fun NavGraphBuilder.homeGraph(appNavState: AppNavState) {
    composable(Destination.Home.route) {
        HomeScaffold(
            currentRoute = Destination.Home.route,
            topBarTitle = "Home",
            navigateToBottomTab = appNavState::navigateToBottomTab,
            onNavigate = appNavState::navigate
        ) {
            HomeScreen()
        }
    }
}

fun NavGraphBuilder.scheduleGraph(appNavState: AppNavState) {
    composable(Destination.Schedule.route) {
        HomeScaffold(
            currentRoute = Destination.Schedule.route,
            topBarTitle = "Schedule",
            navigateToBottomTab = appNavState::navigateToBottomTab
        ) {
            ScheduleScreen()
        }
    }
}

fun NavGraphBuilder.galleryGraph(appNavState: AppNavState) {
    composable(Destination.Gallery.route) {
        HomeScaffold(
            currentRoute = Destination.Gallery.route,
            topBarTitle = "Gallery",
            navigateToBottomTab = appNavState::navigateToBottomTab
        ) {
            GalleryScreen()
        }
    }
}

fun NavGraphBuilder.profileGraph(appNavState: AppNavState) {
    composable(Destination.Profile.route) {
        HomeScaffold(
            currentRoute = Destination.Profile.route,
            topBarTitle = "Profile",
            navigateToBottomTab = appNavState::navigateToBottomTab,
            openAndPopUp = appNavState::openAndPopUp
        ) {
            ProfileScreen(
                navigate = { route -> appNavState.navigate(route) },
                onSignOut = { appNavState.signOutToSplashScreen() }
            )
        }
    }
    composable(Destination.Setting.route) {
        SettingsScreen(
            onBackClick = { appNavState.popUp() },
            openAndPopUp = { route, popUp -> appNavState.openAndPopUp(route, popUp) }
        )
    }
}