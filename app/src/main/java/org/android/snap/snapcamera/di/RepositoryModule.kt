package org.android.snap.snapcamera.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.android.snap.core.domain.repository.AuthRepository
import org.android.snap.snapcamera.data.repository.AuthRepositoryImpl
import org.android.snap.snapcamera.data.repository.CurrentSubjectRepositoryImpl
import org.android.snap.snapcamera.data.repository.ImageNoteRepositoryImpl
import org.android.snap.snapcamera.data.repository.LectureImageRepositoryImpl
import org.android.snap.snapcamera.data.repository.PdfExportRepositoryImpl
import org.android.snap.snapcamera.data.repository.ScheduleRepositoryImpl
import org.android.snap.snapcamera.data.repository.SemesterRepositoryImpl
import org.android.snap.snapcamera.data.repository.StudySessionRepositoryImpl
import org.android.snap.snapcamera.data.repository.SubjectRepositoryImpl
import org.android.snap.snapcamera.data.repository.SubscriptionRepositoryImpl
import org.android.snap.snapcamera.data.repository.SyncRepositoryImpl
import org.android.snap.snapcamera.data.repository.impl.OnBoardingRepository
import org.android.snap.snapcamera.data.repository.impl.UserPreferenceRepository
import org.android.snap.snapcamera.domain.usecase.CurrentSubjectUseCase
import org.android.snap.snapcamera.domain.usecase.ImageNoteUseCase
import org.android.snap.snapcamera.domain.usecase.LectureImageUseCase
import org.android.snap.snapcamera.domain.usecase.OnBoardingUseCase
import org.android.snap.snapcamera.domain.usecase.PdfExportUseCase
import org.android.snap.snapcamera.domain.usecase.ScheduleUseCase
import org.android.snap.snapcamera.domain.usecase.SemesterUseCase
import org.android.snap.snapcamera.domain.usecase.StudySessionUseCase
import org.android.snap.snapcamera.domain.usecase.SubjectUseCase
import org.android.snap.snapcamera.domain.usecase.SubscriptionUseCase
import org.android.snap.snapcamera.domain.usecase.SyncUseCase
import org.android.snap.snapcamera.domain.usecase.UserPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.onboarding.ReadOnBoardingUseCase
import org.android.snap.snapcamera.domain.usecase.onboarding.SaveOnBoardingUseCase
import org.android.snap.snapcamera.domain.usecase.preference.DeletePreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.GetBooleanPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.GetIntPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.GetPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.SetBooleanPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.SetIntPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.SetPreferenceUseCase
import javax.inject.Singleton

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideAuthRepository(
		auth: FirebaseAuth,
		credential: CredentialManager,
		@ApplicationContext appContext: Context,
	): AuthRepository = AuthRepositoryImpl(
		auth = auth,
		appContext = appContext,
		credentialManager = credential,
	)

	@Provides
	@Singleton
	fun provideOnBoardingRepository(
		repository: OnBoardingRepository,
	): OnBoardingUseCase = OnBoardingUseCase(
		readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
		saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
	)

	@Provides
	@Singleton
	fun provideUserPreferenceRepository(
		repository: UserPreferenceRepository,
	): UserPreferenceUseCase = UserPreferenceUseCase(
		setPreferenceUseCase = SetPreferenceUseCase(repository),
		getPreferenceUseCase = GetPreferenceUseCase(repository),
		deletePreferenceUseCase = DeletePreferenceUseCase(repository),
		setBooleanPreferenceUseCase = SetBooleanPreferenceUseCase(repository),
		getBooleanPreferenceUseCase = GetBooleanPreferenceUseCase(repository),
		setIntPreferenceUseCase = SetIntPreferenceUseCase(repository),
		getIntPreferenceUseCase = GetIntPreferenceUseCase(repository),
	)

	// Study Session Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideStudySessionUseCase(
		studySessionRepositoryImpl: StudySessionRepositoryImpl,
	): StudySessionUseCase = StudySessionUseCase(studySessionRepositoryImpl)

	// Subject Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideSubjectUseCase(
		subjectRepositoryImpl: SubjectRepositoryImpl,
	): SubjectUseCase = SubjectUseCase(subjectRepositoryImpl)

	// Current Subject Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideCurrentSubjectUseCase(
		currentSubjectRepositoryImpl: CurrentSubjectRepositoryImpl,
	): CurrentSubjectUseCase = CurrentSubjectUseCase(currentSubjectRepositoryImpl)

	// Schedule Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideScheduleUseCase(
		scheduleRepositoryImpl: ScheduleRepositoryImpl,
	): ScheduleUseCase = ScheduleUseCase(scheduleRepositoryImpl)

	// Semester Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideSemesterUseCase(
		semesterRepositoryImpl: SemesterRepositoryImpl,
	): SemesterUseCase = SemesterUseCase(semesterRepositoryImpl)

	// Lecture Image Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideLectureImageUseCase(
		lectureImageRepositoryImpl: LectureImageRepositoryImpl,
	): LectureImageUseCase = LectureImageUseCase(lectureImageRepositoryImpl)

	// Image Note Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideImageNoteUseCase(
		imageNoteRepositoryImpl: ImageNoteRepositoryImpl,
	): ImageNoteUseCase = ImageNoteUseCase(imageNoteRepositoryImpl)

	// PDF Export Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun providePdfExportUseCase(
		pdfExportRepositoryImpl: PdfExportRepositoryImpl,
	): PdfExportUseCase = PdfExportUseCase(pdfExportRepositoryImpl)

	// Subscription Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideSubscriptionUseCase(
		subscriptionRepositoryImpl: SubscriptionRepositoryImpl,
	): SubscriptionUseCase = SubscriptionUseCase(subscriptionRepositoryImpl)

	// Sync Use Case
	@RequiresApi(Build.VERSION_CODES.O)
	@Provides
	@Singleton
	fun provideSyncUseCase(
		syncRepositoryImpl: SyncRepositoryImpl,
	): SyncUseCase = SyncUseCase(syncRepositoryImpl)
}
