package org.campusquest.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.campusquest.core.domain.model.Schedule
import java.time.LocalTime

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

interface ScheduleRepository {
    suspend fun getActiveSchedules(semesterId: String): List<Schedule>
    suspend fun getSchedulesByDay(dayOfWeek: Int, semesterId: String): List<Schedule>
    suspend fun getCurrentSchedule(semesterId: String): Schedule?
    suspend fun insertSchedule(schedule: Schedule)
    suspend fun insertSchedules(schedules: List<Schedule>)
    suspend fun updateSchedule(schedule: Schedule)
    suspend fun deleteSchedule(schedule: Schedule)
    suspend fun deactivateSchedule(scheduleId: String)
    suspend fun getActiveSubjects(semesterId: String): List<String>
    suspend fun getScheduleById(id: String): Schedule?
}