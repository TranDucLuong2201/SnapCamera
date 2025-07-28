package org.android.snap.core.data.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.android.snap.core.data.dao.ImageNoteDao
import org.android.snap.core.data.dao.LectureImageDao
import org.android.snap.core.data.dao.PdfExportDao
import org.android.snap.core.data.dao.ScheduleItemDao
import org.android.snap.core.data.dao.SemesterDao
import org.android.snap.core.data.dao.StudySessionDao
import org.android.snap.core.data.dao.SubjectDao
import org.android.snap.core.data.dao.SubscriptionDao
import org.android.snap.core.data.dao.SyncStatusDao
import org.android.snap.core.data.dao.UserDao
import org.android.snap.core.data.dao.UserPreferenceDao
import org.android.snap.core.data.entities.ImageNoteEntity
import org.android.snap.core.data.entities.LectureImageEntity
import org.android.snap.core.data.entities.PdfExportEntity
import org.android.snap.core.data.entities.ScheduleItemEntity
import org.android.snap.core.data.entities.SemesterEntity
import org.android.snap.core.data.entities.StudySessionEntity
import org.android.snap.core.data.entities.SubjectEntity
import org.android.snap.core.data.entities.SubscriptionEntity
import org.android.snap.core.data.entities.SyncStatusEntity
import org.android.snap.core.data.entities.UserEntity
import org.android.snap.core.data.entities.UserPreferenceEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
@Database(
	entities = [
		SemesterEntity::class,
		SubjectEntity::class,
		ScheduleItemEntity::class,
		StudySessionEntity::class,
		LectureImageEntity::class,
		ImageNoteEntity::class,
		PdfExportEntity::class,
		UserPreferenceEntity::class,
		SyncStatusEntity::class,
		SubscriptionEntity::class,
		UserEntity::class,
	],
	version = 2,
	exportSchema = false,
)
@TypeConverters(
	DatabaseConverters::class,
	DateConverters::class,
	EnumConverters::class,
)
abstract class StudyNotesDatabase : RoomDatabase() {

	abstract fun semesterDao(): SemesterDao
	abstract fun subjectDao(): SubjectDao
	abstract fun scheduleItemDao(): ScheduleItemDao
	abstract fun studySessionDao(): StudySessionDao
	abstract fun lectureImageDao(): LectureImageDao
	abstract fun imageNoteDao(): ImageNoteDao
	abstract fun pdfExportDao(): PdfExportDao
	abstract fun userPreferenceDao(): UserPreferenceDao
	abstract fun syncStatusDao(): SyncStatusDao
	abstract fun subscriptionDao(): SubscriptionDao
	abstract fun userDao(): UserDao

	companion object {
		const val DATABASE_NAME = "study_notes_database"
	}
}
