package org.campusquest.core.utils

import org.campusquest.android.R
import org.campusquest.core.presentation.onboarding.OnBoarding

object Constant {
    const val PREFERENCES_NAME = "wordflow_preference"
    const val PREFERENCES_KEY = "on_boarding_completed"
    val ON_BOARDING_MODEL = listOf<OnBoarding>(
        OnBoarding(
            image = R.drawable.group_1,
            title = "Challenge Friends & Level Up",
            content = " Team up or compete with friends on vocabulary challenges, earn badges, and climb leaderboards daily. Friendly competition keeps learning fun and motivating."
        ),
        OnBoarding(
            image = R.drawable.group_2,
            title = "Learn Anywhere, Anytime",
            content = "Access nearly 6,000 Oxford‑level words—complete with real‑sentence examples and crisp pronunciations—wherever you go. Whether you're commuting, on a break, or winding down, your next lesson is always ready."
        ),
        OnBoarding(
            image = R.drawable.group_3,
            title = "Offline Dictionary + Online Pronunciation",
            content = "Enjoy our comprehensive offline dictionary with over 5,500 Oxford words, and tap play for online pronunciation anytime. Seamless learning, no matter your connection."
        )
    )
}