package org.android.snap.core.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
import org.android.snap.core.data.database.StudyNotesDatabase
import org.android.snap.core.utils.CurrentSubjectDetector
import javax.inject.Singleton

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
@RequiresApi(Build.VERSION_CODES.O)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideDatabase(
		@ApplicationContext context: Context,
	): StudyNotesDatabase {
		return Room.databaseBuilder(
			context,
			StudyNotesDatabase::class.java,
			StudyNotesDatabase.DATABASE_NAME,
		)
			.build()
	}

	@Provides
	fun provideSemesterDao(database: StudyNotesDatabase): SemesterDao = database.semesterDao()

	@Provides
	fun provideSubjectDao(database: StudyNotesDatabase): SubjectDao = database.subjectDao()

	@Provides
	fun provideScheduleItemDao(database: StudyNotesDatabase): ScheduleItemDao = database.scheduleItemDao()

	@Provides
	fun provideStudySessionDao(database: StudyNotesDatabase): StudySessionDao = database.studySessionDao()

	@Provides
	fun provideLectureImageDao(database: StudyNotesDatabase): LectureImageDao = database.lectureImageDao()

	@Provides
	fun provideImageNoteDao(database: StudyNotesDatabase): ImageNoteDao = database.imageNoteDao()

	@Provides
	fun providePdfExportDao(database: StudyNotesDatabase): PdfExportDao = database.pdfExportDao()

	@Provides
	fun provideUserPreferenceDao(database: StudyNotesDatabase): UserPreferenceDao = database.userPreferenceDao()

	@Provides
	fun provideSyncStatusDao(database: StudyNotesDatabase): SyncStatusDao = database.syncStatusDao()

	@Provides
	fun provideSubscriptionDao(database: StudyNotesDatabase): SubscriptionDao = database.subscriptionDao()

	@Provides
	fun provideUserDao(database: StudyNotesDatabase): UserDao = database.userDao()

	@Provides
	@Singleton
	fun provideCurrentSubjectDetector(
		subjectDao: SubjectDao,
		scheduleItemDao: ScheduleItemDao,
	): CurrentSubjectDetector = CurrentSubjectDetector(subjectDao, scheduleItemDao)
}
