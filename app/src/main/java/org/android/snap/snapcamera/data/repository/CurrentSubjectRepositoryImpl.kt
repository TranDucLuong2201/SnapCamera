package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.map
import org.android.snap.core.data.dao.ScheduleItemDao
import org.android.snap.core.data.dao.SubjectDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.domain.model.ScheduleItem
import org.android.snap.core.domain.model.Subject
import org.android.snap.core.utils.CurrentSubjectDetector
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

/**
 * Repository for current subject detection and schedule management
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class CurrentSubjectRepositoryImpl @Inject constructor(
	private val currentSubjectDetector: CurrentSubjectDetector,
	private val subjectDao: SubjectDao,
	private val scheduleItemDao: ScheduleItemDao,
) {

	/**
	 * Get current subject based on current time
	 */
	suspend fun getCurrentSubject(): Result<Subject?> =
		try {
			val subject = currentSubjectDetector.getCurrentSubject()
			Result.success(subject?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get next subject
	 */
	suspend fun getNextSubject(): Result<Subject?> =
		try {
			val subject = currentSubjectDetector.getNextSubject()
			Result.success(subject?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get current schedule item
	 */
	suspend fun getCurrentScheduleItem(): Result<ScheduleItem?> =
		try {
			val scheduleItem = currentSubjectDetector.getCurrentScheduleItem()
			Result.success(scheduleItem?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get next schedule item
	 */
	suspend fun getNextScheduleItem(): Result<ScheduleItem?> =
		try {
			val scheduleItem = currentSubjectDetector.getNextScheduleItem()
			Result.success(scheduleItem?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get today's subjects
	 */
	suspend fun getTodaySubjects(): Result<List<Subject>> =
		try {
			val subjects = currentSubjectDetector.getTodaySubjects()
			Result.success(subjects.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get today's schedule
	 */
	suspend fun getTodaySchedule(): Result<List<ScheduleItem>> =
		try {
			val scheduleItems = currentSubjectDetector.getTodaySchedule()
			Result.success(scheduleItems.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Check if currently in class
	 */
	suspend fun isCurrentlyInClass(): Result<Boolean> =
		try {
			val isInClass = currentSubjectDetector.isCurrentlyInClass()
			Result.success(isInClass)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get remaining time for current class (minutes)
	 */
	suspend fun getRemainingTimeForCurrentClass(): Result<Int?> =
		try {
			val remainingMinutes = currentSubjectDetector.getRemainingTimeForCurrentClass()
			Result.success(remainingMinutes)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get time until next class (minutes)
	 */
	suspend fun getTimeUntilNextClass(): Result<Int?> =
		try {
			val waitingMinutes = currentSubjectDetector.getTimeUntilNextClass()
			Result.success(waitingMinutes)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get current status with all information
	 */
	suspend fun getCurrentStatus(): Result<CurrentSubjectDetector.CurrentStatus> =
		try {
			val status = currentSubjectDetector.getCurrentStatus()
			Result.success(status)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get schedule for specific date
	 */
	suspend fun getScheduleForDate(date: LocalDate): Result<List<ScheduleItem>> =
		try {
			val scheduleItems = currentSubjectDetector.getScheduleForDate(date)
			Result.success(scheduleItems.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get schedule in time range
	 */
	suspend fun getScheduleInTimeRange(
		dayOfWeek: DayOfWeek,
		startTime: LocalTime,
		endTime: LocalTime,
	): Result<List<ScheduleItem>> =
		try {
			val scheduleItems = currentSubjectDetector.getScheduleInTimeRange(dayOfWeek, startTime, endTime)
			Result.success(scheduleItems.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get current subject for specific day and time
	 */
	suspend fun getCurrentSubjectForDayAndTime(dayOfWeek: DayOfWeek, currentTime: LocalTime): Result<Subject?> =
		try {
			val subject = subjectDao.getCurrentSubject(dayOfWeek, currentTime)
			Result.success(subject?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get next subject for specific day and time
	 */
	suspend fun getNextSubjectForDayAndTime(dayOfWeek: DayOfWeek, currentTime: LocalTime): Result<Subject?> =
		try {
			val subject = subjectDao.getNextSubject(dayOfWeek, currentTime)
			Result.success(subject?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	/**
	 * Get today's subjects for specific day
	 */
	suspend fun getTodaySubjectsForDay(dayOfWeek: DayOfWeek): Result<List<Subject>> =
		try {
			val subjects = subjectDao.getTodaySubjects(dayOfWeek)
			Result.success(subjects.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
