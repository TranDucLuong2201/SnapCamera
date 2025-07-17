package org.campusquest.core.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun rememberBottomBarState(): BottomBarState {
    return remember { BottomBarState() }
}

class BottomBarState {
    var isVisible by mutableStateOf(true)
        private set

    var lastScrollPosition by mutableIntStateOf(0)
        private set

    private var scrollThreshold = 100f
    private var hideDelay = 1000L // 1 second delay before hiding
    private var lastScrollDirection by mutableIntStateOf(0) // 0: none, 1: up, -1: down

    fun updateScrollPosition(newPosition: Int) {
        val scrollDelta = newPosition - lastScrollPosition
        lastScrollPosition = newPosition

        when {
            scrollDelta < -scrollThreshold -> {
                // Scrolling up
                lastScrollDirection = 1
                isVisible = true
            }
            scrollDelta > scrollThreshold -> {
                // Scrolling down
                lastScrollDirection = -1
                isVisible = false
            }
        }
    }

    fun show() {
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }

    suspend fun autoHideAfterDelay() {
        delay(hideDelay)
        // Only auto-hide if the last scroll direction was down and we're not at the top
        if (lastScrollDirection == -1 && lastScrollPosition > 0) {
            isVisible = false
        }
    }
}

@SuppressLint("UnrememberedMutableState", "FrequentlyChangedStateReadInComposition")
@Composable
fun LazyListState.BottomBarVisibilityEffect(
    bottomBarState: BottomBarState
) {
    val isScrollInProgress by derivedStateOf { isScrollInProgress }

    LaunchedEffect(firstVisibleItemScrollOffset, firstVisibleItemIndex) {
        val currentPosition = firstVisibleItemIndex * 1000 + firstVisibleItemScrollOffset
        bottomBarState.updateScrollPosition(currentPosition)
    }

    LaunchedEffect(isScrollInProgress) {
        if (!isScrollInProgress) {
            bottomBarState.autoHideAfterDelay()
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ModernBottomNavigationItem(
    item: NavigationItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val iconScale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.92f
            selected -> 1.1f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "iconScale"
    )

    val iconTint by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else LocalContentColor.current.copy(alpha = 0.6f),
        animationSpec = tween(250),
        label = "iconTint"
    )

    val offsetY by animateDpAsState(
        targetValue = if (selected) (-2).dp else 0.dp,
        animationSpec = tween(250),
        label = "offsetY"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                    onClick()
                }
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .offset(y = offsetY)
                .scale(iconScale),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }

        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(tween(250)) + scaleIn(initialScale = 0.9f),
            exit = fadeOut(tween(200)) + scaleOut(targetScale = 0.8f)
        ) {
            Text(
                text = item.label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = iconTint,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun ModernBottomNavigation(
    modifier: Modifier = Modifier,
    bottomBarState: BottomBarState? = null,
    content: @Composable RowScope.() -> Unit
) {
    val density = LocalDensity.current

    if (bottomBarState != null) {
        AnimatedVisibility(
            visible = bottomBarState.isVisible,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) { with(density) { 100.dp.roundToPx() } } + fadeIn(tween(300)),
            exit = slideOutVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) { with(density) { 100.dp.roundToPx() } } + fadeOut(tween(200)),
            modifier = modifier
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp).padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    } else {
        // Original behavior without auto-hide
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp).padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}