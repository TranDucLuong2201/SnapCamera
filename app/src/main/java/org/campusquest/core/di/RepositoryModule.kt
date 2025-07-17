package org.campusquest.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.campusquest.core.data.repository.ExamRepositoryImpl
import org.campusquest.core.data.repository.FileRepositoryImpl
import org.campusquest.core.data.repository.NotificationRepositoryImpl
import org.campusquest.core.data.repository.PhotoRepositoryImpl
import org.campusquest.core.data.repository.ScheduleRepositoryImpl
import org.campusquest.core.data.repository.SemesterRepositoryImpl
import org.campusquest.core.data.repository.UserRepositoryImpl
import org.campusquest.core.domain.repository.ExamRepository
import org.campusquest.core.domain.repository.FileRepository
import org.campusquest.core.domain.repository.NotificationRepository
import org.campusquest.core.domain.repository.PhotoRepository
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.repository.SemesterRepository
import org.campusquest.core.domain.repository.UserRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    abstract fun bindPhotoRepository(
        photoRepositoryImpl: PhotoRepositoryImpl
    ): PhotoRepository

    @Binds
    abstract fun bindExamRepository(
        examRepositoryImpl: ExamRepositoryImpl
    ): ExamRepository

    @Binds
    abstract fun bindSemesterRepository(
        semesterRepositoryImpl: SemesterRepositoryImpl
    ): SemesterRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindFileRepository(
        fileRepositoryImpl: FileRepositoryImpl
    ): FileRepository
}