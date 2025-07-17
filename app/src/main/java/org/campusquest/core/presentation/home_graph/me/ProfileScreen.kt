package org.campusquest.core.presentation.home_graph.me

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.campusquest.android.R
import org.campusquest.core.domain.model.User
import org.campusquest.core.presentation.ui.components.SmallTopBar
import org.campusquest.core.presentation.ui.components.TopBarAction
import org.campusquest.core.presentation.ui.navigate.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigate: (String) -> Unit,
    onSignOut: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SmallTopBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            when {
                userState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                userState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userState.error ?: "Lỗi không xác định",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                userState.user != null -> {
                    UserProfileContent(
                        user = userState.user!!,
                        onSignOut = {
                            coroutineScope.launch {
                                viewModel.signOut(context)
                                onSignOut()
                            }
                        },
                        navigate = navigate
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Chưa đăng nhập",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileContent(
    user: User,
    onSignOut: () -> Unit,
    navigate: (String) -> Unit
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar with decorative elements
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Decorative circles
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .offset(x = (-70).dp, y = (-30).dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .offset(x = 80.dp, y = (-20).dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .offset(x = (-90).dp, y = 50.dp)
                            .background(MaterialTheme.colorScheme.tertiary, CircleShape)
                    )

                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(
                                width = 3.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                ),
                                shape = CircleShape
                            )
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user.photoUrl.takeIf { !it.isNullOrBlank() } ?: R.drawable.user)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                Text(
                    text = user.displayName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email/Username
                Text(
                    text = "@${user.email.substringBefore("@")}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Join Date
                Text(
                    text = "Tham gia: ${user.joinDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Statistics
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatisticItem(
                        number = "5",
                        label = "Môn học hôm nay",
                        icon = Icons.Default.Task,
                        color = MaterialTheme.colorScheme.primary
                    )
                    StatisticItem(
                        number = "25",
                        label = "Tổng số môn kì này",
                        icon = Icons.Default.Work,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu Items
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuItemRow(
                    title = "My Projects",
                    icon = Icons.Default.Work,
                    onClick = {  }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MenuItemRow(
                    title = "Join a Team",
                    icon = Icons.Default.Group,
                    onClick = {  }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MenuItemRow(
                    title = "Settings",
                    icon = Icons.Default.Settings,
                    onClick = { viewModel.openScreen(navigate, Destination.Setting.route) }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MenuItemRow(
                    title = "My Task",
                    icon = Icons.Default.Task,
                    onClick = {  }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign-out Button
        var isPressed by rememberSaveable { mutableStateOf(false) }
        val interactionSource = remember { MutableInteractionSource() }
        val isPressedState by interactionSource.collectIsPressedAsState()
        val scale by animateFloatAsState(
            targetValue = if (isPressedState) 0.95f else 1f,
            animationSpec = spring(dampingRatio = 0.8f),
            label = "buttonScale"
        )
        val buttonColor by animateColorAsState(
            targetValue = if (isPressedState) MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
            else MaterialTheme.colorScheme.error,
            animationSpec = tween(200),
            label = "buttonColor"
        )

        Button(
            onClick = onSignOut,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .scale(scale)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { isPressed = isPressedState },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Đăng xuất",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}

@Composable
fun StatisticItem(
    number: String,
    label: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = number,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(80.dp)
        )
    }
}

@Composable
fun MenuItemRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = 0.8f),
        label = "menuItemScale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}