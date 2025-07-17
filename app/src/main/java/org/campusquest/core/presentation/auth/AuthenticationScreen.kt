@file:OptIn(ExperimentalAnimationApi::class)

package org.campusquest.core.presentation.auth

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import org.campusquest.android.R
import kotlin.math.sin
import kotlin.random.Random

data class Feature(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val color: Color
)

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    clearAndNavigate: (String) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val activity = LocalActivity.current
    val hasUser = viewModel.hasUser.collectAsState()
    val errorMessage = viewModel.errorMessage
    var currentFeatureIndex by remember { mutableIntStateOf(0) }
    var loadingProgress by remember { mutableFloatStateOf(0f) }
    var showContent by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val features = listOf(
        Feature(
            icon = Icons.Default.Psychology,
            title = "Exam Scheduler",
            description = "Add midterm and final exam dates for each subject",
            color = Color(0xFF3B82F6)
        ),
        Feature(
            icon = Icons.Default.Book,
            title = "Export ZIP",
            description = "Bundle photos by subject/date and share instantly",
            color = Color(0xFF8B5CF6)
        ),
        Feature(
            icon = Icons.Default.Group,
            title = "Semester Reset",
            description = "Clear old data and start a new semester",
            color = Color(0xFF10B981)
        )
    )


    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val glowAnimation by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "glow"
    )
    val scaleAnimation by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(3000), RepeatMode.Reverse),
        label = "scale"
    )
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(hasUser.value) {
        if (hasUser.value) {
            viewModel.onClearAndNavigate(clearAndNavigate)
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        showContent = true
        for (i in 0..100) {
            loadingProgress = i / 100f
            delay(30)
        }
        delay(500)
    }

    LaunchedEffect(showContent) {
        if (showContent) {
            while (true) {
                delay(2000)
                currentFeatureIndex = (currentFeatureIndex + 1) % features.size
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MistyFlowParticles()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = showContent,
                enter = scaleIn(tween(800), initialScale = 0.3f) + fadeIn(tween(800))
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .scale(scaleAnimation)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    MaterialTheme.colorScheme.onBackground.copy(alpha = glowAnimation * 0.3f),
                                    Color.Transparent
                                ),
                                radius = 150f
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_snapcam),
                            contentDescription = null,
                            modifier = Modifier.size(90.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = showContent,
                enter = slideInVertically(tween(1000, 300)) + fadeIn(tween(1000, 300))
            ) {
                Text(
                    text = "WordFlow",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = showContent,
                enter = slideInVertically(tween(1000, 500)) + fadeIn(tween(1000, 500))
            ) {
                Text(
                    text = "Build Your Vocabulary, Build Your Future",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(
                visible = showContent,
                enter = slideInVertically(tween(1000, 700)) + fadeIn(tween(1000, 700))
            ) {
                FeatureShowcase(
                    features = features,
                    currentIndex = currentFeatureIndex,
                    modifier = Modifier.height(100.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(
                visible = loadingProgress >= 1f,
                enter = slideInVertically(tween(1000, 900)) + fadeIn(tween(1000, 900))
            ) {
                if (hasUser.value) {
                    // Không hiển thị gì nếu đã có user, vì sẽ điều hướng ngay
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.signInWithGoogle()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp)
                        ) {
                            Text("Sign in with Google")
                        }
                        OutlinedButton(
                            onClick = { viewModel.signInWithMicrosoft(activity = activity!!) },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp)
                        ) {
                            Text("Sign in with Microsoft")
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = loadingProgress < 1f,
                enter = slideInVertically(tween(1000, 900)) + fadeIn(tween(1000, 900)),
                exit = slideOutVertically(tween(1000, 900)) + fadeOut(tween(1000, 900))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        progress = { loadingProgress },
                        modifier = Modifier
                            .width(200.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Loading... ${(loadingProgress * 100).toInt()}%",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}

@Composable
fun FeatureShowcase(features: List<Feature>, currentIndex: Int, modifier: Modifier = Modifier) {
    val currentFeature = features[currentIndex]
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = currentFeature,
            transitionSpec = {
                (slideInHorizontally(tween(500)) { it } + fadeIn(tween(500)))
                    .togetherWith(slideOutHorizontally(tween(500), { -it }) + fadeOut(tween(500)))
            },
            label = "feature_content"
        ) { feature ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    tint = feature.color,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = feature.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = feature.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                    )
                }
            }
        }
    }
}

@Composable
fun MistyFlowParticles() {
    val particleCount = 25
    val infiniteColorTransition = rememberInfiniteTransition(label = "color_transition")
    val gradientColor by infiniteColorTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.secondary,
        animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing), RepeatMode.Reverse),
        label = "gradient_color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
            )
    ) {
        repeat(particleCount) { index ->
            val seed = remember { Random(index) }
            val size = remember { (6..12).random(seed) }
            val yOffset = remember { (3000..5000).random(seed) }
            val xDuration = remember { (6000..10000).random(seed) }
            val alphaDuration = remember { (4000..6000).random(seed) }

            val infiniteTransition = rememberInfiniteTransition(label = "particle_$index")
            val offsetX by infiniteTransition.animateFloat(
                initialValue = -50f,
                targetValue = 400f,
                animationSpec = infiniteRepeatable(
                    tween(xDuration, easing = LinearEasing),
                    RepeatMode.Restart
                ),
                label = "x_$index"
            )
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 80f,
                animationSpec = infiniteRepeatable(
                    tween(yOffset, easing = FastOutSlowInEasing),
                    RepeatMode.Reverse
                ),
                label = "y_$index"
            )
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 0.8f,
                animationSpec = infiniteRepeatable(
                    tween(alphaDuration, easing = LinearEasing),
                    RepeatMode.Reverse
                ),
                label = "alpha_$index"
            )
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    tween(3000, easing = LinearEasing),
                    RepeatMode.Reverse
                ),
                label = "scale_$index"
            )

            Box(
                modifier = Modifier
                    .offset(
                        x = (index * 30).dp + offsetX.dp,
                        y = (100 + index * 20).dp + sin(offsetX * 0.04f).dp * 40f + offsetY.dp
                    )
                    .size(size.dp)
                    .alpha(alpha)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                gradientColor.copy(alpha = 0.8f),
                                gradientColor.copy(alpha = 0.0f)
                            ),
                            radius = 15f
                        )
                    )
            )
        }
    }
}