package org.campusquest.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.campusquest.core.data.local.entities.NotificationEntity
import org.campusquest.core.data.local.entities.NotificationType

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY scheduledTime DESC")
    suspend fun getAllNotifications(): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE isRead = 0 ORDER BY scheduledTime DESC")
    suspend fun getUnreadNotifications(): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE type = :type ORDER BY scheduledTime DESC")
    suspend fun getNotificationsByType(type: NotificationType): List<NotificationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Update
    suspend fun updateNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markAsRead(notificationId: String)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notifications WHERE scheduledTime < :timestamp")
    suspend fun deleteOldNotifications(timestamp: Long)
}