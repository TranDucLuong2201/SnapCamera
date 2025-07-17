package org.campusquest.core.data.repository

import org.campusquest.core.data.local.dao.ScheduleDao
import org.campusquest.core.data.mapper.toDomain
import org.campusquest.core.data.mapper.toEntity
import org.campusquest.core.domain.model.Schedule
import org.campusquest.core.domain.repository.ScheduleRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class ScheduleRepositoryImpl(
    private val scheduleDao: ScheduleDao
) : ScheduleRepository {

    override suspend fun getActiveSchedules(semesterId: String): List<Schedule> {
        return scheduleDao.getActiveSchedules(semesterId).map { it.toDomain() }
    }

    override suspend fun getSchedulesByDay(dayOfWeek: Int, semesterId: String): List<Schedule> {
        return scheduleDao.getSchedulesByDay(dayOfWeek, semesterId).map { it.toDomain() }
    }

    override suspend fun getCurrentSchedule(semesterId: String): Schedule? {
        val now = Calendar.getInstance()
        val currentDay = now.get(Calendar.DAY_OF_WEEK)
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now.time)

        return scheduleDao.getCurrentSchedule(currentTime, currentDay, semesterId)?.toDomain()
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleDao.insertSchedule(schedule.toEntity())
    }

    override suspend fun insertSchedules(schedules: List<Schedule>) {
        scheduleDao.insertSchedules(schedules.map { it.toEntity() })
    }

    override suspend fun updateSchedule(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule.toEntity())
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        scheduleDao.deleteSchedule(schedule.toEntity())
    }

    override suspend fun deactivateSchedule(scheduleId: String) {
        scheduleDao.deactivateSchedule(scheduleId)
    }

    override suspend fun getActiveSubjects(semesterId: String): List<String> {
        return scheduleDao.getActiveSubjects(semesterId)
    }

    override suspend fun getScheduleById(id: String): Schedule? {
        return scheduleDao.getScheduleById(id)?.toDomain()
    }
}