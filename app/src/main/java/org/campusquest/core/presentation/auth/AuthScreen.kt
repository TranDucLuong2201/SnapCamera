package org.campusquest.core.presentation.auth

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.campusquest.android.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ConfigurationScreenWidthHeight")
@Composable
fun LoginScreen(viewModel: AuthViewModel,) {
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val context = LocalContext.current
    val activity = context as? Activity
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val buttonWidthFraction = if (screenWidth < 360.dp) 0.9f else 0.7f

    // Animated gradient background
    val gradientAlpha by animateFloatAsState(
        targetValue = if (isLoading) 0.7f else 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOut)),
        label = "gradientPulse"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(gradientAlpha)
            .padding(horizontal = if (screenWidth < 360.dp) 16.dp else 24.dp)
            .padding(top = 48.dp, bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo and Welcome Text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { -it / 2 }) + fadeIn(animationSpec = tween(1000)),
                    exit = slideOutVertically(targetOffsetY = { -it / 2 }) + fadeOut(animationSpec = tween(1000))
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "CampusQuest Logo",
                        modifier = Modifier
                            .size(if (screenWidth < 360.dp) 100.dp else 120.dp)
                            .padding(bottom = 16.dp)
                    )
                }
                Text(
                    text = "Welcome to CampusQuest 🎓",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = if (screenWidth < 360.dp) 24.sp else 28.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(1200)),
                    exit = fadeOut(animationSpec = tween(1200))
                ) {
                    Text(
                        text = "Sign in to start your learning journey",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = if (screenWidth < 360.dp) 16.sp else 18.sp
                        ),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Login Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(
                    visible = !isLoading,
                    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(animationSpec = tween(800)),
                    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(animationSpec = tween(800))
                ) {
                    Button(
                        onClick = { viewModel.signInWithGoogle() },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth(buttonWidthFraction)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_google),
                                contentDescription = "Google Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Sign in with Google",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = !isLoading,
                    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(animationSpec = tween(800,200)),
                    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(animationSpec = tween(800))
                ) {
                    OutlinedButton(
                        onClick = { activity?.let { viewModel.signInWithMicrosoft(it) } },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth(buttonWidthFraction)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_microsoft),
                                contentDescription = "Microsoft Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Sign in with Microsoft",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }

            // Loading and Error States
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(animationSpec = tween(400)),
                    exit = fadeOut(animationSpec = tween(400))
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }
                AnimatedVisibility(
                    visible = errorMessage != null,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(animationSpec = tween(400)),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(animationSpec = tween(400))
                ) {
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(buttonWidthFraction)
                        )
                    }
                }
            }
        }
    }
}