package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.database.relation.SubjectWithSchedule
import org.android.snap.core.data.database.relation.SubjectWithSessions
import org.android.snap.core.data.entities.SubjectEntity
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface SubjectDao {
	@Query("SELECT * FROM subjects WHERE semesterId = :semesterId ORDER BY name ASC")
	fun getSubjectsBySemester(semesterId: String): Flow<List<SubjectEntity>>

	@Query("SELECT * FROM subjects WHERE isCompleted = 0 ORDER BY name ASC")
	fun getActiveSubjects(): Flow<List<SubjectEntity>>

	@Query("SELECT * FROM subjects WHERE id = :id")
	suspend fun getSubjectById(id: String): SubjectEntity?

	@Transaction
	@Query("SELECT * FROM subjects WHERE id = :id")
	suspend fun getSubjectWithSchedule(id: String): SubjectWithSchedule?

	@Transaction
	@Query("SELECT * FROM subjects WHERE id = :id")
	suspend fun getSubjectWithSessions(id: String): SubjectWithSessions?

	// Current Subject Detection
	@Query(
		"""
		SELECT s.* FROM subjects s
		INNER JOIN schedule_items si ON s.id = si.subjectId
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.startTime <= :currentTime
		AND si.endTime >= :currentTime
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
		LIMIT 1
	""",
	)
	suspend fun getCurrentSubject(dayOfWeek: DayOfWeek, currentTime: LocalTime): SubjectEntity?

	@Query(
		"""
		SELECT s.* FROM subjects s
		INNER JOIN schedule_items si ON s.id = si.subjectId
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.startTime > :currentTime
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
		LIMIT 1
	""",
	)
	suspend fun getNextSubject(dayOfWeek: DayOfWeek, currentTime: LocalTime): SubjectEntity?

	@Query(
		"""
		SELECT s.* FROM subjects s
		INNER JOIN schedule_items si ON s.id = si.subjectId
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
	""",
	)
	suspend fun getTodaySubjects(dayOfWeek: DayOfWeek): List<SubjectEntity>

	// Search and Filter
	@Query("SELECT * FROM subjects WHERE name LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%'")
	suspend fun searchSubjects(query: String): List<SubjectEntity>

	@Query("SELECT * FROM subjects WHERE teacher LIKE '%' || :teacher || '%'")
	suspend fun getSubjectsByTeacher(teacher: String): List<SubjectEntity>

	@Query("SELECT * FROM subjects WHERE credits = :credits")
	suspend fun getSubjectsByCredits(credits: Int): List<SubjectEntity>

	// Statistics
	@Query("SELECT COUNT(*) FROM subjects WHERE semesterId = :semesterId")
	suspend fun getSubjectCountForSemester(semesterId: String): Int

	@Query("SELECT COUNT(*) FROM subjects WHERE isCompleted = 1")
	suspend fun getCompletedSubjectCount(): Int

	@Query("SELECT COUNT(*) FROM subjects WHERE isCompleted = 0")
	suspend fun getActiveSubjectCount(): Int

	@Query("SELECT AVG(credits) FROM subjects WHERE semesterId = :semesterId AND credits IS NOT NULL")
	suspend fun getAverageCreditsForSemester(semesterId: String): Double?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSubject(subject: SubjectEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSubjects(subjects: List<SubjectEntity>)

	@Update
	suspend fun updateSubject(subject: SubjectEntity)

	@Query("UPDATE subjects SET isCompleted = 1, completedAt = :completedAt WHERE id = :id")
	suspend fun markAsCompleted(id: String, completedAt: LocalDateTime)

	@Query("UPDATE subjects SET isCompleted = 0, completedAt = NULL WHERE id = :id")
	suspend fun markAsIncomplete(id: String)

	@Delete
	suspend fun deleteSubject(subject: SubjectEntity)

	@Query("DELETE FROM subjects WHERE semesterId = :semesterId")
	suspend fun deleteSubjectsBySemester(semesterId: String)
}
