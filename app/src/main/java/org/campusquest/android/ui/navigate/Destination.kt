package org.campusquest.android.ui.navigate

sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object OnBoarding : Destination("onboarding")
    object Authentication : Destination("authentication")

    // Home Flow
    object Home : Destination("home")

    // Home Sub-destinations
    object Dictionary : Destination("dictionary")
    object Explore : Destination("explore")
    object Profile : Destination("profile")
    object AILearning : Destination("ai_learning")
}
