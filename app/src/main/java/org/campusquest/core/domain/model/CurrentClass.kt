package org.campusquest.core.domain.model

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class CurrentClass(
    val schedule: Schedule?,
    val isActive: Boolean,
    val timeRemaining: String,
    val nextClass: Schedule?
) {
    fun getStatusMessage(): String = when {
        isActive && schedule != null -> "Đang học: ${schedule.subject}"
        nextClass != null -> "Tiết tiếp theo: ${nextClass.subject} lúc ${nextClass.startTime}"
        else -> "Không có lịch học hôm nay"
    }
}