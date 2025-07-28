package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.dao.StudySessionDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.StudySession
import org.android.snap.core.domain.repository.StudySessionRepository
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */
@RequiresApi(Build.VERSION_CODES.O)
class StudySessionRepositoryImpl @Inject constructor(
	private val studySessionDao: StudySessionDao,
	private val auth: FirebaseAuth,
) : StudySessionRepository {

	override fun getSessionsBySubject(subjectId: String): Flow<Result<List<StudySession>>> =
		studySessionDao.getSessionsBySubject(subjectId).map { sessionEntities ->
			Result.success(sessionEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getSessionsByDate(date: LocalDate): Result<List<StudySession>> =
		try {
			val sessions = studySessionDao.getSessionsByDate(date)
			Result.success(sessions.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override fun getSessionsByDateRange(
		startDate: LocalDate,
		endDate: LocalDate,
	): Flow<Result<List<StudySession>>> =
		studySessionDao.getSessionsByDateRange(startDate, endDate).map { sessionEntities ->
			Result.success(sessionEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getSessionById(id: String): Result<StudySession?> =
		try {
			val session = studySessionDao.getSessionById(id)
			Result.success(session?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createSession(session: StudySession): Result<StudySession> =
		try {
			val sessionEntity = session.toEntity()
			studySessionDao.insertSession(sessionEntity)
			Result.success(session)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateSession(session: StudySession): Result<Unit> =
		try {
			val sessionEntity = session.toEntity()
			studySessionDao.updateSession(sessionEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteSession(sessionId: String): Result<Unit> =
		try {
			val session = studySessionDao.getSessionById(sessionId)
			session?.let { studySessionDao.deleteSession(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateLiveMode(
		sessionId: String,
		isLive: Boolean,
	): Result<Unit> =
		try {
			studySessionDao.updateLiveMode(sessionId, isLive)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun getOrCreateTodaySession(subjectId: String): Result<StudySession> =
		try {
			val today = LocalDate.now()
			val existingSession = studySessionDao.getSessionByDateAndSubject(today, subjectId)

			if (existingSession != null) {
				Result.success(existingSession.toDomain())
			} else {
				val newSession = StudySession(
					id = generateSessionId(),
					subjectId = subjectId,
					date = today,
					sessionNumber = 1,
					title = null,
					description = null,
					isLiveMode = false,
					startTime = LocalDateTime.now(),
					endTime = null,
					createdAt = LocalDateTime.now(),
					updatedAt = LocalDateTime.now(),
				)
				createSession(newSession)
			}
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncSessions(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	private fun generateSessionId(): String = "session_${System.currentTimeMillis()}_${auth.currentUser?.uid ?: "anonymous"}"
}
