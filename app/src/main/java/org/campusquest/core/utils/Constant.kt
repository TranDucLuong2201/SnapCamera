package org.campusquest.core.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.School
import org.campusquest.android.R
import org.campusquest.core.presentation.ui.navigate.Destination
import org.campusquest.core.presentation.ui.components.NavigationItem
import org.campusquest.core.presentation.onboarding.OnBoarding

object Constant {
    const val PREFERENCES_NAME = "wordflow_preference"
    const val PREFERENCES_KEY = "on_boarding_completed"
    val ON_BOARDING_MODEL = listOf(
        OnBoarding(
            image = R.drawable.onboarding_schedule,
            title = "Set Up Your Timetable",
            content = "Input your weekly class schedule once. The app will auto-track your sessions and suggest which subject you're currently attending."
        ),
        OnBoarding(
            image = R.drawable.onboarding_camera,
            title = "Capture Study Moments",
            content = "Take photos during your classes, and we'll organize them by subject and date for easy tracking and documentation."
        ),
        OnBoarding(
            image = R.drawable.onboarding_export,
            title = "Export & Share Instantly",
            content = "At the end of each day or semester, export all your captured photos into a ZIP file and share them with friends in a tap."
        )
    )
    val BOTTOM_NAVIGATION_ITEMS = listOf(
        NavigationItem(
            route = Destination.Home.route,
            icon = Icons.Outlined.Home,
            label = "Today"
        ),
        NavigationItem(
            route = Destination.Schedule.route,
            icon = Icons.AutoMirrored.Outlined.MenuBook,
            label = "Schedule"
        ),
        NavigationItem(
            route = Destination.Gallery.route,
            icon = Icons.Outlined.RateReview,
            label = "Gallery"
        ),
        NavigationItem(
            route = Destination.Profile.route,
            icon = Icons.Outlined.School,
            label = "Profile"
        )
    )
    const val SCHEDULE_TABLE = "schedule"
    const val PHOTO_TABLE = "photo"
    const val EXAM_TABLE = "exam"
}