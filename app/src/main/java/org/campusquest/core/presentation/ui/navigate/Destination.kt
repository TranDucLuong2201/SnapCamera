package org.campusquest.core.presentation.ui.navigate

sealed class Destination(val route: String) {
    data object Splash : Destination("splash")
    data object OnBoarding : Destination("onboarding")
    data object Authentication : Destination("authentication")

    // Home Flow
    data object SetUp : Destination("set_up")
    data object Home : Destination("home")

    data object Profile : Destination("profile")
    data object Gallery : Destination("gallery")
    data object Schedule : Destination("schedule")

    data object Setting : Destination("setting")

}
