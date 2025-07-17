package org.campusquest.core.domain.usecase.manage_usecase

import org.campusquest.core.domain.model.Semester
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.repository.SemesterRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class SemesterManagementUseCase(
    private val semesterRepository: SemesterRepository,
    private val scheduleRepository: ScheduleRepository
) {
    suspend fun startNewSemester(name: String, startDate: Long, endDate: Long): Result<Semester> {
        return try {
            val semester = semesterRepository.createNewSemester(name, startDate, endDate)
            semesterRepository.activateSemester(semester.id)
            Result.success(semester)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentSemester(): Semester? {
        return semesterRepository.getActiveSemester()
    }

    suspend fun isReadyForNewSemester(): Boolean {
        val activeSemester = semesterRepository.getActiveSemester() ?: return true
        val activeSchedules = scheduleRepository.getActiveSchedules(activeSemester.id)
        return activeSchedules.isEmpty()
    }

    suspend fun endCurrentSemester(): Result<Unit> {
        return try {
            val activeSemester = semesterRepository.getActiveSemester()
                ?: return Result.failure(Exception("Không có học kỳ đang hoạt động"))

            val updatedSemester = activeSemester.copy(active = false)
            semesterRepository.updateSemester(updatedSemester)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}