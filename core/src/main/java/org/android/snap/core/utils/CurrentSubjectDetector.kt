package org.android.snap.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.data.dao.ScheduleItemDao
import org.android.snap.core.data.dao.SubjectDao
import org.android.snap.core.data.entities.ScheduleItemEntity
import org.android.snap.core.data.entities.SubjectEntity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

/**
 * Utility class để detect current subject và schedule
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */
@RequiresApi(Build.VERSION_CODES.O)
class CurrentSubjectDetector @Inject constructor(
	private val subjectDao: SubjectDao,
	private val scheduleItemDao: ScheduleItemDao,
) {

	/**
	 * Lấy môn học hiện tại dựa trên thời gian
	 */
	suspend fun getCurrentSubject(): SubjectEntity? {
		val now = LocalDateTime.now()
		val dayOfWeek = now.dayOfWeek
		val currentTime = now.toLocalTime()

		return subjectDao.getCurrentSubject(dayOfWeek, currentTime)
	}

	/**
	 * Lấy môn học tiếp theo
	 */
	suspend fun getNextSubject(): SubjectEntity? {
		val now = LocalDateTime.now()
		val dayOfWeek = now.dayOfWeek
		val currentTime = now.toLocalTime()

		return subjectDao.getNextSubject(dayOfWeek, currentTime)
	}

	/**
	 * Lấy schedule item hiện tại
	 */
	suspend fun getCurrentScheduleItem(): ScheduleItemEntity? {
		val now = LocalDateTime.now()
		val dayOfWeek = now.dayOfWeek
		val currentTime = now.toLocalTime()

		return scheduleItemDao.getCurrentScheduleItem(dayOfWeek, currentTime)
	}

	/**
	 * Lấy schedule item tiếp theo
	 */
	suspend fun getNextScheduleItem(): ScheduleItemEntity? {
		val now = LocalDateTime.now()
		val dayOfWeek = now.dayOfWeek
		val currentTime = now.toLocalTime()

		return scheduleItemDao.getNextScheduleItem(dayOfWeek, currentTime)
	}

	/**
	 * Lấy tất cả môn học của hôm nay
	 */
	suspend fun getTodaySubjects(): List<SubjectEntity> {
		val now = LocalDateTime.now()
		val dayOfWeek = now.dayOfWeek

		return subjectDao.getTodaySubjects(dayOfWeek)
	}

	/**
	 * Lấy tất cả schedule items của hôm nay
	 */
	suspend fun getTodaySchedule(): List<ScheduleItemEntity> {
		val now = LocalDateTime.now()
		val dayOfWeek = now.dayOfWeek

		return scheduleItemDao.getTodaySchedule(dayOfWeek)
	}

	/**
	 * Kiểm tra xem có đang trong giờ học không
	 */
	suspend fun isCurrentlyInClass(): Boolean {
		return getCurrentSubject() != null
	}

	/**
	 * Lấy thời gian còn lại của buổi học hiện tại (phút)
	 */
	suspend fun getRemainingTimeForCurrentClass(): Int? {
		val currentSchedule = getCurrentScheduleItem() ?: return null
		val now = LocalTime.now()
		val endTime = currentSchedule.endTime

		return if (now.isBefore(endTime)) {
			val remainingMinutes = java.time.Duration.between(now, endTime).toMinutes()
			remainingMinutes.toInt()
		} else {
			0
		}
	}

	/**
	 * Lấy thời gian chờ đến buổi học tiếp theo (phút)
	 */
	suspend fun getTimeUntilNextClass(): Int? {
		val nextSchedule = getNextScheduleItem() ?: return null
		val now = LocalTime.now()
		val startTime = nextSchedule.startTime

		return if (now.isBefore(startTime)) {
			val waitingMinutes = java.time.Duration.between(now, startTime).toMinutes()
			waitingMinutes.toInt()
		} else {
			0
		}
	}

	/**
	 * Lấy thông tin chi tiết về trạng thái hiện tại
	 */
	data class CurrentStatus(
		val currentSubject: SubjectEntity?,
		val currentSchedule: ScheduleItemEntity?,
		val nextSubject: SubjectEntity?,
		val nextSchedule: ScheduleItemEntity?,
		val isInClass: Boolean,
		val remainingMinutes: Int?,
		val waitingMinutes: Int?,
	)

	suspend fun getCurrentStatus(): CurrentStatus {
		val currentSubject = getCurrentSubject()
		val currentSchedule = getCurrentScheduleItem()
		val nextSubject = getNextSubject()
		val nextSchedule = getNextScheduleItem()
		val isInClass = currentSubject != null
		val remainingMinutes = getRemainingTimeForCurrentClass()
		val waitingMinutes = getTimeUntilNextClass()

		return CurrentStatus(
			currentSubject = currentSubject,
			currentSchedule = currentSchedule,
			nextSubject = nextSubject,
			nextSchedule = nextSchedule,
			isInClass = isInClass,
			remainingMinutes = remainingMinutes,
			waitingMinutes = waitingMinutes,
		)
	}

	/**
	 * Lấy schedule cho một ngày cụ thể
	 */
	suspend fun getScheduleForDate(date: LocalDate): List<ScheduleItemEntity> {
		val dayOfWeek = date.dayOfWeek
		return scheduleItemDao.getTodaySchedule(dayOfWeek)
	}

	/**
	 * Lấy schedule trong khoảng thời gian
	 */
	suspend fun getScheduleInTimeRange(
		dayOfWeek: DayOfWeek,
		startTime: LocalTime,
		endTime: LocalTime,
	): List<ScheduleItemEntity> {
		return scheduleItemDao.getScheduleInTimeRange(dayOfWeek, startTime, endTime)
	}
}
