package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.database.relation.StudySessionWithImages
import org.android.snap.core.data.entities.StudySessionEntity
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface StudySessionDao {
	@Query("SELECT * FROM study_sessions WHERE subjectId = :subjectId ORDER BY date DESC, sessionNumber ASC")
	fun getSessionsBySubject(subjectId: String): Flow<List<StudySessionEntity>>

	@Query("SELECT * FROM study_sessions WHERE date = :date ORDER BY sessionNumber ASC")
	suspend fun getSessionsByDate(date: LocalDate): List<StudySessionEntity>

	@Query("SELECT * FROM study_sessions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
	fun getSessionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<StudySessionEntity>>

	@Query("SELECT * FROM study_sessions WHERE id = :id")
	suspend fun getSessionById(id: String): StudySessionEntity?

	@Transaction
	@Query("SELECT * FROM study_sessions WHERE id = :id")
	suspend fun getSessionWithImages(id: String): StudySessionWithImages?

	@Query("SELECT * FROM study_sessions WHERE subjectId = :subjectId AND date = :date ORDER BY sessionNumber DESC LIMIT 1")
	suspend fun getLastSessionForSubjectAndDate(subjectId: String, date: LocalDate): StudySessionEntity?

	@Query("SELECT * FROM study_sessions WHERE date = :date AND subjectId = :subjectId")
	suspend fun getSessionByDateAndSubject(date: LocalDate, subjectId: String): StudySessionEntity?

	@Query("SELECT * FROM study_sessions WHERE date >= :fromDate ORDER BY date ASC, sessionNumber ASC")
	fun getUpcomingSessions(fromDate: LocalDate): Flow<List<StudySessionEntity>>

	@Query("SELECT * FROM study_sessions WHERE date = :date AND startTime IS NOT NULL AND endTime IS NOT NULL AND startTime <= :currentTime AND endTime >= :currentTime")
	suspend fun getCurrentSession(date: LocalDate, currentTime: LocalDateTime): StudySessionEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSession(session: StudySessionEntity)

	@Update
	suspend fun updateSession(session: StudySessionEntity)

	@Delete
	suspend fun deleteSession(session: StudySessionEntity)

	@Query("UPDATE study_sessions SET isLiveMode = :isLive WHERE id = :id")
	suspend fun updateLiveMode(id: String, isLive: Boolean)

	@Query("SELECT COUNT(*) FROM study_sessions WHERE subjectId = :subjectId")
	suspend fun getSessionCountForSubject(subjectId: String): Int

	@Query("SELECT COUNT(*) FROM study_sessions WHERE date BETWEEN :startDate AND :endDate")
	suspend fun getSessionCountInDateRange(startDate: LocalDate, endDate: LocalDate): Int
}
