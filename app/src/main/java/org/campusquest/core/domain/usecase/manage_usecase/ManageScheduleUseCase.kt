package org.campusquest.core.domain.usecase.manage_usecase

import org.campusquest.core.domain.model.Schedule
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.repository.SemesterRepository
import java.util.UUID

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class ManageScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val semesterRepository: SemesterRepository
) {
    suspend fun addSchedule(
        subject: String,
        dayOfWeek: Int,
        startTime: String,
        endTime: String,
        room: String = ""
    ): Result<Schedule> {
        return try {
            val activeSemester = semesterRepository.getActiveSemester()
                ?: return Result.failure(Exception("Không có học kỳ đang hoạt động"))

            val schedule = Schedule(
                id = UUID.randomUUID().toString(),
                subject = subject,
                dayOfWeek = dayOfWeek,
                startTime = startTime,
                endTime = endTime,
                room = room,
                active = true,
                semesterId = activeSemester.id,
                createdAt = System.currentTimeMillis()
            )

            scheduleRepository.insertSchedule(schedule)
            Result.success(schedule)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getActiveSchedules(): List<Schedule> {
        val activeSemester = semesterRepository.getActiveSemester()
            ?: return emptyList()
        return scheduleRepository.getActiveSchedules(activeSemester.id)
    }

    suspend fun endCourse(scheduleId: String): Result<Unit> {
        return try {
            scheduleRepository.deactivateSchedule(scheduleId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllActiveSubjects(): List<String> {
        val activeSemester = semesterRepository.getActiveSemester()
            ?: return emptyList()
        return scheduleRepository.getActiveSubjects(activeSemester.id)
    }
}