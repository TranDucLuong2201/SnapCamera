package org.campusquest.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import org.campusquest.core.data.local.AppDatabase
import org.campusquest.core.data.local.dao.ExamDao
import org.campusquest.core.data.local.dao.NotificationDao
import org.campusquest.core.data.local.dao.PhotoDao
import org.campusquest.core.data.local.dao.ScheduleDao
import org.campusquest.core.data.local.dao.SemesterDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "student_photo_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideScheduleDao(database: AppDatabase): ScheduleDao = database.scheduleDao()

    @Provides
    fun providePhotoDao(database: AppDatabase): PhotoDao = database.photoDao()

    @Provides
    fun provideExamDao(database: AppDatabase): ExamDao = database.examDao()

    @Provides
    fun provideSemesterDao(database: AppDatabase): SemesterDao = database.semesterDao()

    @Provides
    fun provideNotificationDao(database: AppDatabase): NotificationDao = database.notificationDao()
}
