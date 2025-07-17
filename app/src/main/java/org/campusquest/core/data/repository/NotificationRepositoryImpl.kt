package org.campusquest.core.data.repository

import org.campusquest.core.data.local.dao.NotificationDao
import org.campusquest.core.data.local.entities.NotificationType
import org.campusquest.core.data.mapper.toDomain
import org.campusquest.core.data.mapper.toEntity
import org.campusquest.core.domain.model.Notification
import org.campusquest.core.domain.repository.NotificationRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override suspend fun getAllNotifications(): List<Notification> {
        return notificationDao.getAllNotifications().map { it.toDomain() }
    }

    override suspend fun getUnreadNotifications(): List<Notification> {
        return notificationDao.getUnreadNotifications().map { it.toDomain() }
    }

    override suspend fun getNotificationsByType(type: NotificationType): List<Notification> {
        return notificationDao.getNotificationsByType(type).map { it.toDomain() }
    }

    override suspend fun insertNotification(notification: Notification) {
        notificationDao.insertNotification(notification.toEntity())
    }

    override suspend fun updateNotification(notification: Notification) {
        notificationDao.updateNotification(notification.toEntity())
    }

    override suspend fun markAsRead(notificationId: String) {
        notificationDao.markAsRead(notificationId)
    }

    override suspend fun markAllAsRead() {
        notificationDao.markAllAsRead()
    }

    override suspend fun deleteOldNotifications(daysOld: Int) {
        val timestamp = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L)
        notificationDao.deleteOldNotifications(timestamp)
    }
}