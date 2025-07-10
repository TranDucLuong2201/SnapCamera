package org.campusquest.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.campusquest.android.ui.navigate.Destination
import org.campusquest.android.ui.navigate.rememberAppState
import org.campusquest.android.ui.navigate.setUpNavGraph
import org.campusquest.android.ui.theme.CampusQuestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusQuestTheme {
                val appState = rememberAppState()
                val navController = rememberNavController()
                Scaffold{
                    NavHost(
                        navController = appState.navController,
                        startDestination = Destination.Splash.route,
                    ) {
                        setUpNavGraph(appState, modifier = Modifier, navController = navController)
                    }
                }
            }
        }
    }
}