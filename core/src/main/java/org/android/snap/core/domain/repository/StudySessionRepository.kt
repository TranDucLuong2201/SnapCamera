package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.StudySession
import java.time.LocalDate

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface StudySessionRepository {
	fun getSessionsBySubject(subjectId: String): Flow<Result<List<StudySession>>>
	suspend fun getSessionsByDate(date: LocalDate): Result<List<StudySession>>
	fun getSessionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<Result<List<StudySession>>>
	suspend fun getSessionById(id: String): Result<StudySession?>
	suspend fun createSession(session: StudySession): Result<StudySession>
	suspend fun updateSession(session: StudySession): Result<Unit>
	suspend fun deleteSession(sessionId: String): Result<Unit>
	suspend fun updateLiveMode(sessionId: String, isLive: Boolean): Result<Unit>
	suspend fun getOrCreateTodaySession(subjectId: String): Result<StudySession>
	suspend fun syncSessions(): Result<SyncResult>
}
