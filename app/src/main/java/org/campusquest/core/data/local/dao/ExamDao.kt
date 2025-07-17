package org.campusquest.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.campusquest.core.data.local.entities.ExamEntity

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface ExamDao {
    @Query("SELECT * FROM exams WHERE semesterId = :semesterId ORDER BY examDate ASC")
    suspend fun getExamsBySemester(semesterId: String): List<ExamEntity>

    @Query("SELECT * FROM exams WHERE examDate BETWEEN :startDate AND :endDate ORDER BY examDate ASC")
    suspend fun getExamsByDateRange(startDate: Long, endDate: Long): List<ExamEntity>

    @Query("SELECT * FROM exams WHERE subject = :subject AND semesterId = :semesterId ORDER BY examDate ASC")
    suspend fun getExamsBySubject(subject: String, semesterId: String): List<ExamEntity>

    @Query("SELECT * FROM exams WHERE examDate >= :currentTime AND isCompleted = 0 ORDER BY examDate ASC LIMIT 5")
    suspend fun getUpcomingExams(currentTime: Long): List<ExamEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExam(exam: ExamEntity)

    @Update
    suspend fun updateExam(exam: ExamEntity)

    @Delete
    suspend fun deleteExam(exam: ExamEntity)

    @Query("UPDATE exams SET isCompleted = :isCompleted WHERE id = :examId")
    suspend fun updateExamStatus(examId: String, isCompleted: Boolean)
}