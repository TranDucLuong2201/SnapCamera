package org.campusquest.core.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

// Data class for TopBar actions
data class TopBarAction(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit,
    val showBadge: Boolean = false
)

// TopBar Action Item Component
@Composable
fun TopBarActionItem(
    action: TopBarAction,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "actionScale"
    )

    val iconTint by animateColorAsState(
        targetValue = if (isPressed)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        animationSpec = tween(200),
        label = "iconTint"
    )

    Box(
        modifier = modifier
            .size(44.dp)
            .scale(scale)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                    action.onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = action.contentDescription,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )

        if (action.showBadge) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-2).dp)
                    .background(
                        MaterialTheme.colorScheme.error,
                        CircleShape
                    )
            )
        }
    }
}

// Simplified Search Action Button
@Composable
fun SearchActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBarActionItem(
        action = TopBarAction(
            icon = Icons.Default.Search,
            contentDescription = "Search",
            onClick = onClick
        ),
        modifier = modifier
    )
}

// Large TopBar for main screens
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LargeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    actions: List<TopBarAction> = emptyList(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearchSubmit: (String) -> Unit = {},
    placeholder: String = "Search...",
    enableSearch: Boolean = false
) {
    var isSearchExpanded by rememberSaveable { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current

    // Animation specs
    val animationSpec: AnimationSpec<Dp> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )

    val cornerRadius by animateDpAsState(
        targetValue = if (isSearchExpanded) 16.dp else 28.dp,
        animationSpec = animationSpec,
        label = "cornerRadius"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSearchExpanded) 8.dp else 4.dp,
        animationSpec = animationSpec,
        label = "elevation"
    )

    // Auto-focus management
    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            delay(300)
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
            keyboardController?.hide()
            if (searchQuery.isEmpty()) {
                onSearchQueryChange("")
            }
        }
    }

    // Handle focus loss
    LaunchedEffect(isFocused) {
        if (!isFocused && isSearchExpanded && searchQuery.isEmpty()) {
            delay(200)
            isSearchExpanded = false
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(if (isSearchExpanded) 2f else 1f),
        color = backgroundColor,
        shape = RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius),
        tonalElevation = elevation
    ) {
        Column {
            Spacer(modifier.height(20.dp).fillMaxWidth())
            AnimatedContent(
                targetState = isSearchExpanded,
                transitionSpec = {
                    if (targetState) {
                        (fadeIn(animationSpec = tween(400, delayMillis = 200)) +
                                slideInVertically(
                                    initialOffsetY = { it / 3 },
                                    animationSpec = tween(500)
                                )).togetherWith(
                            fadeOut(animationSpec = tween(200)) +
                                    slideOutVertically(
                                        targetOffsetY = { -it / 3 },
                                        animationSpec = tween(300)
                                    )
                        )
                    } else {
                        (fadeIn(animationSpec = tween(400, delayMillis = 200)) +
                                slideInVertically(
                                    initialOffsetY = { -it / 3 },
                                    animationSpec = tween(500)
                                )).togetherWith(
                            fadeOut(animationSpec = tween(200)) +
                                    slideOutVertically(
                                        targetOffsetY = { it / 3 },
                                        animationSpec = tween(300)
                                    )
                        )
                    }
                },
                label = "searchExpandCollapse"
            ) { expanded ->
                if (expanded) {
                    SearchContent(
                        searchQuery = searchQuery,
                        onSearchQueryChange = onSearchQueryChange,
                        onSearchSubmit = onSearchSubmit,
                        onBackClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                            isSearchExpanded = false
                            onSearchQueryChange("")
                        },
                        placeholder = placeholder,
                        contentColor = contentColor,
                        focusRequester = focusRequester,
                        onFocusChanged = { isFocused = it }
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            ),
                            color = contentColor,
                            modifier = Modifier.weight(1f)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (enableSearch) {
                                SearchActionButton(
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                                        isSearchExpanded = true
                                    }
                                )
                            }

                            actions.forEach { action ->
                                TopBarActionItem(action = action)
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun SearchContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    onBackClick: () -> Unit,
    placeholder: String,
    contentColor: Color,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopBarActionItem(
            action = TopBarAction(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                onClick = onBackClick
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 2.dp
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                textStyle = TextStyle(
                    color = contentColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.isNotEmpty()) {
                            onSearchSubmit(searchQuery)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    }
                ),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = contentColor.copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = contentColor.copy(alpha = 0.6f),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }

                        AnimatedVisibility(
                            visible = searchQuery.isNotEmpty(),
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            IconButton(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                                    onSearchQueryChange("")
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = contentColor.copy(alpha = 0.7f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

// Small TopBar for detail screens
@Composable
fun SmallTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    actions: List<TopBarAction> = emptyList(),
    navigationIcon: List<TopBarAction> = emptyList(),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        tonalElevation = 2.dp
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, bottom = 16.dp).padding(horizontal = 16.dp)
        ) {

            // Left icons (navigation)
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                navigationIcon.forEach { action ->
                    TopBarActionItem(action = action)
                }
            }

            // Center title
            title()

            // Right icons (actions)
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions.forEach { action ->
                    TopBarActionItem(action = action)
                }
            }
        }
    }
}

// TopBar with tabs for screens with multiple sections
@Composable
fun TabTopBar(
    title: String,
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    actions: List<TopBarAction> = emptyList(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    ),
                    color = contentColor,
                    modifier = Modifier.weight(1f)
                )

                if (actions.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        actions.forEach { action ->
                            TopBarActionItem(action = action)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    TabItem(
                        text = tab,
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) }
                    )
                }
            }
        }
    }
}

// Tab Item Component
@Composable
private fun TabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "tabScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(250),
        label = "tabBackground"
    )

    val textColor by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(250),
        label = "tabTextColor"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                    onClick()
                }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
            ),
            color = textColor
        )
    }
}

@Composable
fun AnimatedSearchButton(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    searchIcon: ImageVector = Icons.Default.Search,
    clearIcon: ImageVector = Icons.Default.Clear,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    expandedBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    expandedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    collapsedSize: Dp = 44.dp,
    expandedWidth: Dp = 280.dp,
    expandedHeight: Dp = 48.dp,
    cornerRadius: Dp = 24.dp,
    animationDuration: Int = 400,
    autoFocus: Boolean = true,
    onExpandedChange: (Boolean) -> Unit = {}
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current

    // Smooth animations
    val animationSpec: AnimationSpec<Dp> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )

    val width by animateDpAsState(
        targetValue = if (isExpanded) expandedWidth else collapsedSize,
        animationSpec = animationSpec,
        label = "searchWidth"
    )

    val height by animateDpAsState(
        targetValue = if (isExpanded) expandedHeight else collapsedSize,
        animationSpec = animationSpec,
        label = "searchHeight"
    )

    val cornerRadiusAnimated by animateDpAsState(
        targetValue = if (isExpanded) cornerRadius else collapsedSize / 2,
        animationSpec = animationSpec,
        label = "searchCornerRadius"
    )

    val backgroundColorAnimated by animateColorAsState(
        targetValue = if (isExpanded) expandedBackgroundColor else backgroundColor,
        animationSpec = tween(animationDuration / 2),
        label = "searchBackgroundColor"
    )

    val contentColorAnimated by animateColorAsState(
        targetValue = if (isExpanded) expandedContentColor else contentColor,
        animationSpec = tween(animationDuration / 2),
        label = "searchContentColor"
    )

    val elevation by animateDpAsState(
        targetValue = if (isExpanded) 8.dp else 2.dp,
        animationSpec = tween(animationDuration),
        label = "searchElevation"
    )

    LaunchedEffect(isExpanded) {
        if (isExpanded && autoFocus) {
            delay(200)
            focusRequester.requestFocus()
        } else if (!isExpanded) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
        onExpandedChange(isExpanded)
    }

    LaunchedEffect(isFocused) {
        if (!isFocused && isExpanded && searchQuery.isEmpty()) {
            delay(100)
            isExpanded = false
        }
    }

    Surface(
        modifier = modifier
            .size(width = width, height = height)
            .zIndex(if (isExpanded) 1f else 0f),
        color = backgroundColorAnimated,
        shape = RoundedCornerShape(cornerRadiusAnimated),
        tonalElevation = elevation,
        shadowElevation = elevation
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            // Search Icon
            Icon(
                imageVector = searchIcon,
                contentDescription = "Search",
                tint = contentColorAnimated.copy(alpha = 0.8f),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                            if (!isExpanded) {
                                isExpanded = true
                            }
                        }
                    )
            )

            // Search TextField
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = animationDuration / 2,
                        delayMillis = animationDuration / 3
                    )
                ) + slideInHorizontally(
                    initialOffsetX = { it / 3 },
                    animationSpec = tween(animationDuration)
                ),
                exit = fadeOut(
                    animationSpec = tween(animationDuration / 3)
                ) + slideOutHorizontally(
                    targetOffsetX = { it / 3 },
                    animationSpec = tween(animationDuration / 2)
                )
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 44.dp, end = if (searchQuery.isNotEmpty()) 44.dp else 12.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    textStyle = TextStyle(
                        color = contentColorAnimated,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchQuery.isNotEmpty()) {
                                onSearchSubmit(searchQuery)
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = contentColorAnimated.copy(alpha = 0.6f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            // Clear button
            AnimatedVisibility(
                visible = isExpanded && searchQuery.isNotEmpty(),
                enter = fadeIn(
                    animationSpec = tween(200)
                ) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(200)
                ),
                exit = fadeOut(
                    animationSpec = tween(150)
                ) + scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(150)
                ),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                IconButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                        onSearchQueryChange("")
                    },
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = clearIcon,
                        contentDescription = "Clear search",
                        tint = contentColorAnimated.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
