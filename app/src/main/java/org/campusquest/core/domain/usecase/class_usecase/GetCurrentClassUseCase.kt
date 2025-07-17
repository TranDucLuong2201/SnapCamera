package org.campusquest.core.domain.usecase.class_usecase

import org.campusquest.core.domain.model.CurrentClass
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.repository.SemesterRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class GetCurrentClassUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val semesterRepository: SemesterRepository
) {
    suspend operator fun invoke(): CurrentClass {
        val activeSemester = semesterRepository.getActiveSemester()
            ?: return CurrentClass(null, false, "", null)

        val now = Calendar.getInstance()
        val currentDay = now.get(Calendar.DAY_OF_WEEK)
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now.time)

        val todaySchedules = scheduleRepository.getSchedulesByDay(currentDay, activeSemester.id)
        val currentSchedule = todaySchedules.find { schedule ->
            currentTime >= schedule.startTime && currentTime <= schedule.endTime
        }

        val nextSchedule = todaySchedules.find { schedule ->
            currentTime < schedule.startTime
        }

        val timeRemaining = if (currentSchedule != null) {
            calculateTimeRemaining(currentSchedule.endTime, currentTime)
        } else ""

        return CurrentClass(
            schedule = currentSchedule,
            isActive = currentSchedule != null,
            timeRemaining = timeRemaining,
            nextClass = nextSchedule
        )
    }

    private fun calculateTimeRemaining(endTime: String, currentTime: String): String {
        val endHour = endTime.split(":")[0].toInt()
        val endMinute = endTime.split(":")[1].toInt()
        val currentHour = currentTime.split(":")[0].toInt()
        val currentMinute = currentTime.split(":")[1].toInt()

        val endTotalMinutes = endHour * 60 + endMinute
        val currentTotalMinutes = currentHour * 60 + currentMinute

        val remainingMinutes = endTotalMinutes - currentTotalMinutes
        val hours = remainingMinutes / 60
        val minutes = remainingMinutes % 60

        return if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
    }
}