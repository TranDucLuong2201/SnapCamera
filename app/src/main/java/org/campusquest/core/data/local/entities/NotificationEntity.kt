package org.campusquest.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val type: NotificationType,
    val relatedId: String = "", // scheduleId, examId, etc.
    val scheduledTime: Long,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class NotificationType {
    CLASS_REMINDER, EXAM_REMINDER, PHOTO_REMINDER, SYSTEM
}