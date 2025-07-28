package org.android.snap.snapcamera.presentation.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	onNavigateToCurrentSubject: () -> Unit,
	onNavigateToStudySession: () -> Unit,
	onNavigateToSubjects: () -> Unit,
	onNavigateToSchedule: () -> Unit,
	onNavigateToImages: () -> Unit,
	onNavigateToSettings: () -> Unit,
) {}
