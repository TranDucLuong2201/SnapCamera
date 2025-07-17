package org.campusquest.core.domain.model

import org.campusquest.core.data.local.entities.ExamType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class Exam(
    val id: String,
    val subject: String,
    val examDate: Long,
    val type: ExamType,
    val room: String,
    val note: String,
    val scheduleId: String,
    val semesterId: String,
    val isCompleted: Boolean,
    val createdAt: Long
) {
    fun getFormattedDate(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(examDate))

    fun getFormattedTime(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(examDate))

    fun getTypeDisplayName(): String = when (type) {
        ExamType.MIDTERM -> "Giữa kỳ"
        ExamType.FINAL -> "Cuối kỳ"
        ExamType.QUIZ -> "Kiểm tra"
        ExamType.ASSIGNMENT -> "Bài tập"
    }

    fun getDaysUntilExam(): Long {
        val now = System.currentTimeMillis()
        val diff = examDate - now
        return diff / (24 * 60 * 60 * 1000)
    }

    fun isUpcoming(): Boolean = examDate > System.currentTimeMillis() && !isCompleted
}