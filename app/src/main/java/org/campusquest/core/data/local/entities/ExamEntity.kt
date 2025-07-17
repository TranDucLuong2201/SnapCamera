package org.campusquest.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = "exams")
data class ExamEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val subject: String,
    val examDate: Long,
    val type: ExamType,
    val room: String = "",
    val note: String = "",
    val scheduleId: String,
    val semesterId: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ExamType {
    MIDTERM, FINAL, QUIZ, ASSIGNMENT
}