package org.campusquest.core.presentation.home_graph

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import org.campusquest.core.presentation.ui.components.BottomBarVisibilityEffect
import org.campusquest.core.presentation.ui.components.ModernBottomNavigation
import org.campusquest.core.presentation.ui.components.ModernBottomNavigationItem
import org.campusquest.core.presentation.ui.components.rememberBottomBarState
import org.campusquest.core.utils.Constant.BOTTOM_NAVIGATION_ITEMS

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScaffold(
    currentRoute: String,
    topBarTitle: String,
    navigateToBottomTab: (String) -> Unit,
    onNavigate: ((String) -> Unit)? = null,
    openAndPopUp: ((String, String) -> Unit)? = null,
    content: @Composable (LazyListState?) -> Unit // Pass LazyListState to content
) {
    val bottomBarState = rememberBottomBarState()
    val listState = rememberLazyListState()

    // Connect scroll to visibility
    listState.BottomBarVisibilityEffect(bottomBarState)



    Scaffold(
        bottomBar = {
            ModernBottomNavigation(
                bottomBarState = bottomBarState
            ) {
                BOTTOM_NAVIGATION_ITEMS.forEach { item ->
                    ModernBottomNavigationItem(
                        item = item,
                        selected = currentRoute == item.route,
                        onClick = { navigateToBottomTab(item.route) }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        content(listState) // Pass the listState to content
    }
}