package org.campusquest.core.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier,
    pageItems: List<OnBoarding>,
    clearAndNavigate: (String) -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        val pagerState = rememberPagerState(pageCount = { pageItems.size })
        val coroutineScope = rememberCoroutineScope()
        val isLastPage = pagerState.currentPage == pageItems.size - 1

        // Pager content
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize()
        ) { page ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 72.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(
                        animationSpec = tween(
                            800
                        )
                    ),
                    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(
                        animationSpec = tween(
                            800
                        )
                    )
                ) {
                    Image(
                        painter = painterResource(pageItems[page].image),
                        contentDescription = null,
                        modifier = modifier
                            .size(300.dp)
                            .padding(bottom = 24.dp)
                    )
                }
                Text(
                    text = pageItems[page].title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = pageItems[page].content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Skip button (top-right)
        AnimatedVisibility(
            visible = !isLastPage,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500)),
            modifier = modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.saveOnBoardingState(true)
                    viewModel.onNavigateToSignIn(clearAndNavigate)
                }
            ) {
                Text("Skip", style = MaterialTheme.typography.labelLarge)
            }
        }

        // Pager indicator and Next/Get Started button
        Column(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PagerIndicator(
                pageCount = pageItems.size,
                currentPageIndex = pagerState.currentPage,
                modifier = modifier
            )
            Button(
                onClick = {
                    if (isLastPage) {
                        viewModel.saveOnBoardingState(true)
                        viewModel.onNavigateToSignIn(clearAndNavigate)
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = modifier
                    .fillMaxWidth(0.6f)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = if (isLastPage) "Get Started" else "Next",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPageIndex: Int,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(
                        if (currentPageIndex == iteration) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    .size(12.dp)
            )
        }
    }
}