package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.entities.ScheduleItemEntity
import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface ScheduleItemDao {
	@Query("SELECT * FROM schedule_items WHERE subjectId = :subjectId AND isActive = 1")
	fun getScheduleBySubject(subjectId: String): Flow<List<ScheduleItemEntity>>

	@Query("SELECT * FROM schedule_items WHERE dayOfWeek = :dayOfWeek AND isActive = 1")
	suspend fun getScheduleByDay(dayOfWeek: DayOfWeek): List<ScheduleItemEntity>

	@Query("SELECT * FROM schedule_items WHERE id = :id")
	suspend fun getScheduleItemById(id: String): ScheduleItemEntity?

	// Current Subject Detection Queries
	@Query(
		"""
		SELECT si.* FROM schedule_items si
		INNER JOIN subjects s ON si.subjectId = s.id
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.startTime <= :currentTime
		AND si.endTime >= :currentTime
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
		LIMIT 1
	""",
	)
	suspend fun getCurrentScheduleItem(dayOfWeek: DayOfWeek, currentTime: LocalTime): ScheduleItemEntity?

	@Query(
		"""
		SELECT si.* FROM schedule_items si
		INNER JOIN subjects s ON si.subjectId = s.id
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.startTime > :currentTime
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
		LIMIT 1
	""",
	)
	suspend fun getNextScheduleItem(dayOfWeek: DayOfWeek, currentTime: LocalTime): ScheduleItemEntity?

	@Query(
		"""
		SELECT si.* FROM schedule_items si
		INNER JOIN subjects s ON si.subjectId = s.id
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
	""",
	)
	suspend fun getTodaySchedule(dayOfWeek: DayOfWeek): List<ScheduleItemEntity>

	@Query(
		"""
		SELECT si.* FROM schedule_items si
		INNER JOIN subjects s ON si.subjectId = s.id
		WHERE si.dayOfWeek = :dayOfWeek
		AND si.startTime BETWEEN :startTime AND :endTime
		AND si.isActive = 1
		AND s.isCompleted = 0
		ORDER BY si.startTime ASC
	""",
	)
	suspend fun getScheduleInTimeRange(dayOfWeek: DayOfWeek, startTime: LocalTime, endTime: LocalTime): List<ScheduleItemEntity>

	// Conflict Detection
	@Query(
		"""
		SELECT COUNT(*) FROM schedule_items si1
		INNER JOIN schedule_items si2 ON si1.subjectId != si2.subjectId
		WHERE si1.dayOfWeek = si2.dayOfWeek
		AND si1.isActive = 1 AND si2.isActive = 1
		AND (
			(si1.startTime <= si2.startTime AND si1.endTime > si2.startTime)
			OR (si1.startTime < si2.endTime AND si1.endTime >= si2.endTime)
			OR (si1.startTime >= si2.startTime AND si1.endTime <= si2.endTime)
		)
	""",
	)
	suspend fun checkScheduleConflicts(): Int

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertScheduleItem(item: ScheduleItemEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertScheduleItems(items: List<ScheduleItemEntity>)

	@Update
	suspend fun updateScheduleItem(item: ScheduleItemEntity)

	@Delete
	suspend fun deleteScheduleItem(item: ScheduleItemEntity)

	@Query("DELETE FROM schedule_items WHERE subjectId = :subjectId")
	suspend fun deleteSchedulesBySubject(subjectId: String)

	// Statistics Queries
	@Query("SELECT COUNT(*) FROM schedule_items WHERE subjectId = :subjectId AND isActive = 1")
	suspend fun getScheduleCountForSubject(subjectId: String): Int

	@Query("SELECT COUNT(*) FROM schedule_items WHERE dayOfWeek = :dayOfWeek AND isActive = 1")
	suspend fun getScheduleCountForDay(dayOfWeek: DayOfWeek): Int
}
