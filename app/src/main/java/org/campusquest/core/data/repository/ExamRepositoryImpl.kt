package org.campusquest.core.data.repository

import org.campusquest.core.data.local.dao.ExamDao
import org.campusquest.core.data.mapper.toDomain
import org.campusquest.core.data.mapper.toEntity
import org.campusquest.core.domain.model.Exam
import org.campusquest.core.domain.repository.ExamRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class ExamRepositoryImpl(
    private val examDao: ExamDao
) : ExamRepository {

    override suspend fun getExamsBySemester(semesterId: String): List<Exam> {
        return examDao.getExamsBySemester(semesterId).map { it.toDomain() }
    }

    override suspend fun getExamsByDateRange(startDate: Long, endDate: Long): List<Exam> {
        return examDao.getExamsByDateRange(startDate, endDate).map { it.toDomain() }
    }

    override suspend fun getExamsBySubject(subject: String, semesterId: String): List<Exam> {
        return examDao.getExamsBySubject(subject, semesterId).map { it.toDomain() }
    }

    override suspend fun getUpcomingExams(): List<Exam> {
        val currentTime = System.currentTimeMillis()
        return examDao.getUpcomingExams(currentTime).map { it.toDomain() }
    }

    override suspend fun insertExam(exam: Exam) {
        examDao.insertExam(exam.toEntity())
    }

    override suspend fun updateExam(exam: Exam) {
        examDao.updateExam(exam.toEntity())
    }

    override suspend fun deleteExam(exam: Exam) {
        examDao.deleteExam(exam.toEntity())
    }

    override suspend fun updateExamStatus(examId: String, isCompleted: Boolean) {
        examDao.updateExamStatus(examId, isCompleted)
    }
}