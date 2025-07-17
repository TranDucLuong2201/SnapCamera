package org.campusquest.core.domain.usecase.manage_usecase

import org.campusquest.core.data.local.entities.ExamType
import org.campusquest.core.data.local.entities.NotificationType
import org.campusquest.core.domain.model.Exam
import org.campusquest.core.domain.repository.ExamRepository
import org.campusquest.core.domain.repository.ScheduleRepository
import org.campusquest.core.domain.usecase.notification_usecase.CreateNotificationUseCase
import java.util.UUID

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class ManageExamUseCase(
    private val examRepository: ExamRepository,
    private val scheduleRepository: ScheduleRepository,
    private val createNotificationUseCase: CreateNotificationUseCase
) {
    suspend fun addExam(
        subject: String,
        examDate: Long,
        type: ExamType,
        room: String = "",
        note: String = ""
    ): Result<Exam> {
        return try {
            val schedule = scheduleRepository.getActiveSubjects("")
                .firstOrNull { it == subject }
                ?: return Result.failure(Exception("Môn học không tồn tại"))

            val exam = Exam(
                id = UUID.randomUUID().toString(),
                subject = subject,
                examDate = examDate,
                type = type,
                room = room,
                note = note,
                scheduleId = "",
                semesterId = "",
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )

            examRepository.insertExam(exam)

            // Tạo thông báo nhắc nhở
            createNotificationUseCase(
                title = "Lịch thi sắp tới",
                message = "Môn ${subject} - ${exam.getTypeDisplayName()}",
                type = NotificationType.EXAM_REMINDER,
                relatedId = exam.id,
                scheduledTime = examDate - (24 * 60 * 60 * 1000) // 1 ngày trước
            )

            Result.success(exam)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUpcomingExams(): List<Exam> {
        return examRepository.getUpcomingExams()
    }

    suspend fun markExamCompleted(examId: String): Result<Unit> {
        return try {
            examRepository.updateExamStatus(examId, true)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
