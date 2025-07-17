package org.campusquest.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.campusquest.core.domain.repository.ExamRepository
import org.campusquest.core.domain.repository.FileRepository
import org.campusquest.core.domain.repository.NotificationRepository
import org.campusquest.core.domain.repository.PhotoRepository
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.repository.SemesterRepository
import org.campusquest.core.domain.usecase.class_usecase.GetCurrentClassUseCase
import org.campusquest.core.domain.usecase.class_usecase.GetTodayScheduleUseCase
import org.campusquest.core.domain.usecase.export_usecase.ExportPhotosUseCase
import org.campusquest.core.domain.usecase.notification_usecase.CreateNotificationUseCase
import org.campusquest.core.domain.usecase.savephot_usecase.SavePhotoUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCurrentClassUseCase(
        scheduleRepository: ScheduleRepository,
        semesterRepository: SemesterRepository
    ): GetCurrentClassUseCase {
        return GetCurrentClassUseCase(scheduleRepository, semesterRepository)
    }

    @Provides
    fun provideSavePhotoUseCase(
        photoRepository: PhotoRepository,
        fileRepository: FileRepository,
        getCurrentClassUseCase: GetCurrentClassUseCase
    ): SavePhotoUseCase {
        return SavePhotoUseCase(photoRepository, fileRepository, getCurrentClassUseCase)
    }

    @Provides
    fun provideExportPhotosUseCase(
        photoRepository: PhotoRepository,
        fileRepository: FileRepository
    ): ExportPhotosUseCase {
        return ExportPhotosUseCase(photoRepository, fileRepository)
    }

    @Provides
    fun provideGetTodayScheduleUseCase(
        scheduleRepository: ScheduleRepository,
        semesterRepository: SemesterRepository
    ): GetTodayScheduleUseCase {
        return GetTodayScheduleUseCase(scheduleRepository, semesterRepository)
    }

    @Provides
    fun provideCreateNotificationUseCase(
        notificationRepository: NotificationRepository
    ): CreateNotificationUseCase {
        return CreateNotificationUseCase(notificationRepository)
    }

    @Provides
    fun provideManageScheduleUseCase(
        scheduleRepository: ScheduleRepository,
        semesterRepository: SemesterRepository
    ): ManageScheduleUseCase {
        return ManageScheduleUseCase(scheduleRepository, semesterRepository)
    }

    @Provides
    fun provideManageExamUseCase(
        examRepository: ExamRepository,
        scheduleRepository: ScheduleRepository,
        createNotificationUseCase: CreateNotificationUseCase
    ): ManageExamUseCase {
        return ManageExamUseCase(examRepository, scheduleRepository, createNotificationUseCase)
    }

    @Provides
    fun providePhotoGalleryUseCase(
        photoRepository: PhotoRepository,
        fileRepository: FileRepository
    ): PhotoGalleryUseCase {
        return PhotoGalleryUseCase(photoRepository, fileRepository)
    }

    @Provides
    fun provideSemesterManagementUseCase(
        semesterRepository: SemesterRepository,
        scheduleRepository: ScheduleRepository
    ): SemesterManagementUseCase {
        return SemesterManagementUseCase(semesterRepository, scheduleRepository)
    }
}