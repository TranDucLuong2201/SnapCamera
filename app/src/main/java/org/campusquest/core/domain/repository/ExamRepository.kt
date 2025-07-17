package org.campusquest.core.domain.repository

import org.campusquest.core.domain.model.Exam

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface ExamRepository {
    suspend fun getExamsBySemester(semesterId: String): List<Exam>
    suspend fun getExamsByDateRange(startDate: Long, endDate: Long): List<Exam>
    suspend fun getExamsBySubject(subject: String, semesterId: String): List<Exam>
    suspend fun getUpcomingExams(): List<Exam>
    suspend fun insertExam(exam: Exam)
    suspend fun updateExam(exam: Exam)
    suspend fun deleteExam(exam: Exam)
    suspend fun updateExamStatus(examId: String, isCompleted: Boolean)
}