package org.android.snap.snapcamera.presentation.ui.nav

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

sealed class Destination(val route: String) {
	data object Splash : Destination("splash")
	data object Onboarding : Destination("onboarding")
	data object Login : Destination("login")
	data object Home : Destination("home")
	data object CurrentSubject : Destination("current_subject")
	data object StudySession : Destination("study_session")
	data object Subjects : Destination("subjects")
	data object Schedule : Destination("schedule")
	data object Images : Destination("images")
	data object Settings : Destination("settings")
	data object Camera : Destination("camera")
	data object ImageDetail : Destination("image_detail/{imageId}") {
		fun createRoute(imageId: String) = "image_detail/$imageId"
	}
	data object SubjectDetail : Destination("subject_detail/{subjectId}") {
		fun createRoute(subjectId: String) = "subject_detail/$subjectId"
	}
}
