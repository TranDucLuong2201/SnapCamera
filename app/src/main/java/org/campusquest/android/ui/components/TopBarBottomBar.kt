package org.campusquest.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordFlowTopBar(
    onProfileClick : () -> Unit,
    onSettingsClick : () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "WordFlow",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f)
                    )
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        modifier = Modifier
            .shadow(8.dp, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    )
}

@Composable
fun ModernBottomNavigation(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val navigationItems = listOf(
        NavigationItem("home", Icons.Filled.Home, "Home"),
        NavigationItem("learn", Icons.Filled.Book, "Learn"),
        NavigationItem("progress", Icons.Filled.Star, "Progress"),
        NavigationItem("explore", Icons.Filled.Search, "Explore")
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationItems.forEach { item ->
                ModernBottomNavigationItem(
                    item = item,
                    selected = selectedTab == item.route,
                    onClick = { onTabSelected(item.route) }
                )
            }
        }
    }
}

@Composable
fun ModernBottomNavigationItem(
    item: NavigationItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val animatedScale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.95f
            selected -> 1.1f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = 300f
        ),
        label = "scale"
    )

    val animatedColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.6f
        ),
        animationSpec = tween(300),
        label = "color"
    )

    val backgroundAlpha by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.25f
            selected -> 0.15f
            else -> 0f
        },
        animationSpec = tween(200),
        label = "background"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        awaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(52.dp)
                .scale(animatedScale)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = backgroundAlpha),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = animatedColor,
                modifier = Modifier.size(26.dp)
            )
        }

        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(animationSpec = tween(300)) +
                    slideInHorizontally(animationSpec = tween(300)) +
                    scaleIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(200)) +
                    slideOutHorizontally(animationSpec = tween(200)) +
                    scaleOut(animationSpec = tween(200))
        ) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                color = animatedColor,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)