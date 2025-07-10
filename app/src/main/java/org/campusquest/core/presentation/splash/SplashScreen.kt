package org.campusquest.core.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import org.campusquest.android.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit,
) {
    val onBoardingCompleted by viewModel.onBoardingCompleted.collectAsState()
    LaunchedEffect(Unit) {
        delay(1000)
        if (onBoardingCompleted) {
            viewModel.onGetStartedClick(openAndPopUp)
        } else {
            viewModel.onDefault(openAndPopUp)
        }

    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_wordflow),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}
