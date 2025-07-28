package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.domain.model.ScheduleItem
import org.android.snap.core.domain.model.Subject
import org.android.snap.core.utils.CurrentSubjectDetector
import org.android.snap.snapcamera.data.repository.CurrentSubjectRepositoryImpl
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

/**
 * Use case for Current Subject operations
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class CurrentSubjectUseCase @Inject constructor(
	private val currentSubjectRepository: CurrentSubjectRepositoryImpl,
) {

	/**
	 * Get current subject based on current time
	 */
	suspend fun getCurrentSubject(): Result<Subject?> =
		currentSubjectRepository.getCurrentSubject()

	/**
	 * Get next subject
	 */
	suspend fun getNextSubject(): Result<Subject?> =
		currentSubjectRepository.getNextSubject()

	/**
	 * Get current schedule item
	 */
	suspend fun getCurrentScheduleItem(): Result<ScheduleItem?> =
		currentSubjectRepository.getCurrentScheduleItem()

	/**
	 * Get next schedule item
	 */
	suspend fun getNextScheduleItem(): Result<ScheduleItem?> =
		currentSubjectRepository.getNextScheduleItem()

	/**
	 * Get today's subjects
	 */
	suspend fun getTodaySubjects(): Result<List<Subject>> =
		currentSubjectRepository.getTodaySubjects()

	/**
	 * Get today's schedule
	 */
	suspend fun getTodaySchedule(): Result<List<ScheduleItem>> =
		currentSubjectRepository.getTodaySchedule()

	/**
	 * Check if currently in class
	 */
	suspend fun isCurrentlyInClass(): Result<Boolean> =
		currentSubjectRepository.isCurrentlyInClass()

	/**
	 * Get remaining time for current class (minutes)
	 */
	suspend fun getRemainingTimeForCurrentClass(): Result<Int?> =
		currentSubjectRepository.getRemainingTimeForCurrentClass()

	/**
	 * Get time until next class (minutes)
	 */
	suspend fun getTimeUntilNextClass(): Result<Int?> =
		currentSubjectRepository.getTimeUntilNextClass()

	/**
	 * Get current status with all information
	 */
	suspend fun getCurrentStatus(): Result<CurrentSubjectDetector.CurrentStatus> =
		currentSubjectRepository.getCurrentStatus()

	/**
	 * Get schedule for specific date
	 */
	suspend fun getScheduleForDate(date: LocalDate): Result<List<ScheduleItem>> =
		currentSubjectRepository.getScheduleForDate(date)

	/**
	 * Get schedule in time range
	 */
	suspend fun getScheduleInTimeRange(
		dayOfWeek: DayOfWeek,
		startTime: LocalTime,
		endTime: LocalTime,
	): Result<List<ScheduleItem>> =
		currentSubjectRepository.getScheduleInTimeRange(dayOfWeek, startTime, endTime)

	/**
	 * Get current subject for specific day and time
	 */
	suspend fun getCurrentSubjectForDayAndTime(dayOfWeek: DayOfWeek, currentTime: LocalTime): Result<Subject?> =
		currentSubjectRepository.getCurrentSubjectForDayAndTime(dayOfWeek, currentTime)

	/**
	 * Get next subject for specific day and time
	 */
	suspend fun getNextSubjectForDayAndTime(dayOfWeek: DayOfWeek, currentTime: LocalTime): Result<Subject?> =
		currentSubjectRepository.getNextSubjectForDayAndTime(dayOfWeek, currentTime)

	/**
	 * Get today's subjects for specific day
	 */
	suspend fun getTodaySubjectsForDay(dayOfWeek: DayOfWeek): Result<List<Subject>> =
		currentSubjectRepository.getTodaySubjectsForDay(dayOfWeek)
}
