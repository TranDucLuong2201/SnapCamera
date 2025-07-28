package org.android.snap.snapcamera

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import dagger.hilt.android.AndroidEntryPoint
import org.android.snap.snapcamera.presentation.ui.nav.AppNavGraph
import org.android.snap.snapcamera.presentation.ui.nav.rememberAppState
import org.android.snap.snapcamera.presentation.ui.theme.SnapCameraTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			SnapCameraTheme {
				val appState = rememberAppState()
				Scaffold {
					AppNavGraph(
						navController = appState.navController,
					)
				}
			}
		}
	}
}
