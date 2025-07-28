package org.android.snap.core.utils

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

object DbConstants {

	object Semester {
		const val TABLE_NAME = "semesters"
	}

	object Subject {
		const val TABLE_NAME = "subjects"
		const val FK_PARENT = "id"
		const val FK_CHILD = "semesterId"
	}
	object ScheduleItem {
		const val TABLE_NAME = "schedule_items"
		const val FK_PARENT = "id"
		const val FK_CHILD = "subjectId"
	}
	object StudySession {
		const val TABLE_NAME = "study_sessions"
		const val FK_PARENT = "id"
		const val FK_CHILD = "subjectId"
	}

	object LectureImage {
		const val TABLE_NAME = "lecture_images"
		const val FK_PARENT = "id"
		const val FK_CHILD = "sessionId"
	}

	object ImageNote {
		const val TABLE_NAME = "image_notes"
		const val FK_PARENT = "id"
		const val FK_CHILD = "imageId"
	}

	object PdfExport {
		const val TABLE_NAME = "pdf_exports"
	}

	object User {
		const val TABLE_NAME = "users"
	}
	object UserPreference {
		const val TABLE_NAME = "user_preferences"
	}

	object SyncStatus {
		const val TABLE_NAME = "sync_status"
	}

	object Subscription {
		const val TABLE_NAME = "subscription_info"
	}
}
