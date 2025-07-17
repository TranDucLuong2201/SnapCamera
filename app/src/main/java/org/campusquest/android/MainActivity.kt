package org.campusquest.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dagger.hilt.android.AndroidEntryPoint
import org.campusquest.core.presentation.ui.navigate.Destination
import org.campusquest.core.presentation.ui.navigate.setUpNavGraph
import org.campusquest.core.presentation.ui.navigate.rememberAppState
import org.campusquest.core.presentation.ui.theme.CampusQuestTheme
import org.campusquest.core.presentation.auth.AuthenticationScreen
import org.campusquest.core.presentation.onboarding.OnboardingScreen
import org.campusquest.core.presentation.splash.SplashScreen
import org.campusquest.core.utils.Constant.ON_BOARDING_MODEL

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusQuestTheme {
                val appState = rememberAppState()
                Scaffold {
                    NavHost(
                        navController = appState.navController,
                        startDestination = Destination.Splash.route,
                    ) {
                        composable(Destination.Splash.route) {
                            SplashScreen(
                                openAndPopUp = { route, popUp ->
                                    appState.openAndPopUp(
                                        route,
                                        popUp
                                    )
                                }
                            )
                        }
                        composable(Destination.OnBoarding.route) {
                            OnboardingScreen(
                                pageItems = ON_BOARDING_MODEL,
                                clearAndNavigate = { route -> appState.clearAndNavigate(route) },
                            )
                        }
                        composable(Destination.Authentication.route) {
                            AuthenticationScreen(
                                clearAndNavigate = { route -> appState.clearAndNavigate(route) },
                            )
                        }
                        setUpNavGraph(appState)
                    }
                }
            }
        }
    }
}