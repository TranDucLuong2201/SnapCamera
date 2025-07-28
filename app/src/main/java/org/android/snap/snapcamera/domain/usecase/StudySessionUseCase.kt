package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.domain.model.StudySession
import org.android.snap.snapcamera.data.repository.StudySessionRepositoryImpl
import java.time.LocalDate
import javax.inject.Inject

/**
 * Use case for StudySession operations
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class StudySessionUseCase @Inject constructor(
	private val studySessionRepository: StudySessionRepositoryImpl,
) {

	/**
	 * Get sessions by subject
	 */
	fun getSessionsBySubject(subjectId: String): Flow<Result<List<StudySession>>> =
		studySessionRepository.getSessionsBySubject(subjectId)

	/**
	 * Get sessions by date
	 */
	suspend fun getSessionsByDate(date: LocalDate): Result<List<StudySession>> =
		studySessionRepository.getSessionsByDate(date)

	/**
	 * Get sessions by date range
	 */
	fun getSessionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<Result<List<StudySession>>> =
		studySessionRepository.getSessionsByDateRange(startDate, endDate)

	/**
	 * Get session by ID
	 */
	suspend fun getSessionById(id: String): Result<StudySession?> =
		studySessionRepository.getSessionById(id)

	/**
	 * Create new session
	 */
	suspend fun createSession(session: StudySession): Result<StudySession> =
		studySessionRepository.createSession(session)

	/**
	 * Update session
	 */
	suspend fun updateSession(session: StudySession): Result<Unit> =
		studySessionRepository.updateSession(session)

	/**
	 * Delete session
	 */
	suspend fun deleteSession(sessionId: String): Result<Unit> =
		studySessionRepository.deleteSession(sessionId)

	/**
	 * Update live mode
	 */
	suspend fun updateLiveMode(sessionId: String, isLive: Boolean): Result<Unit> =
		studySessionRepository.updateLiveMode(sessionId, isLive)

	/**
	 * Get or create today's session for a subject
	 */
	suspend fun getOrCreateTodaySession(subjectId: String): Result<StudySession> =
		studySessionRepository.getOrCreateTodaySession(subjectId)

	/**
	 * Sync sessions
	 */
	suspend fun syncSessions(): Result<org.android.snap.core.common.SyncResult> =
		studySessionRepository.syncSessions()
}
