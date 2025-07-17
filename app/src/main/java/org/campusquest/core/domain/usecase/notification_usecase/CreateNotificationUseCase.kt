package org.campusquest.core.domain.usecase.notification_usecase

import org.campusquest.core.data.local.entities.NotificationType
import org.campusquest.core.domain.model.Notification
import org.campusquest.core.domain.repository.NotificationRepository
import java.util.UUID

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class CreateNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        title: String,
        message: String,
        type: NotificationType,
        relatedId: String = "",
        scheduledTime: Long = System.currentTimeMillis()
    ) {
        val notification = Notification(
            id = UUID.randomUUID().toString(),
            title = title,
            message = message,
            type = type,
            relatedId = relatedId,
            scheduledTime = scheduledTime,
            isRead = false,
            createdAt = System.currentTimeMillis()
        )

        notificationRepository.insertNotification(notification)
    }
}