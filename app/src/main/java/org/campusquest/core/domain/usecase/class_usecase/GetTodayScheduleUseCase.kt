package org.campusquest.core.domain.usecase.class_usecase

import org.campusquest.core.domain.model.Schedule
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.repository.SemesterRepository
import java.util.Calendar

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class GetTodayScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val semesterRepository: SemesterRepository
) {
    suspend operator fun invoke(): List<Schedule> {
        val activeSemester = semesterRepository.getActiveSemester()
            ?: return emptyList()

        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return scheduleRepository.getSchedulesByDay(today, activeSemester.id)
    }
}