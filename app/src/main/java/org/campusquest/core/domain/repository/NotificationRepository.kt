package org.campusquest.core.domain.repository

import org.campusquest.core.data.local.entities.NotificationType
import org.campusquest.core.domain.model.Notification

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface NotificationRepository {
    suspend fun getAllNotifications(): List<Notification>
    suspend fun getUnreadNotifications(): List<Notification>
    suspend fun getNotificationsByType(type: NotificationType): List<Notification>
    suspend fun insertNotification(notification: Notification)
    suspend fun updateNotification(notification: Notification)
    suspend fun markAsRead(notificationId: String)
    suspend fun markAllAsRead()
    suspend fun deleteOldNotifications(daysOld: Int = 30)
}