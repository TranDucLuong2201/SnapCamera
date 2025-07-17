package org.campusquest.core.domain.model

import org.campusquest.core.data.local.entities.NotificationType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val relatedId: String,
    val scheduledTime: Long,
    val isRead: Boolean,
    val createdAt: Long
) {
    fun getFormattedTime(): String =
        SimpleDateFormat("HH:mm dd/MM", Locale.getDefault()).format(Date(scheduledTime))

    fun getTimeAgo(): String {
        val now = System.currentTimeMillis()
        val diff = now - scheduledTime

        return when {
            diff < 60 * 1000 -> "Vừa xong"
            diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)} phút trước"
            diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)} giờ trước"
            else -> "${diff / (24 * 60 * 60 * 1000)} ngày trước"
        }
    }
}