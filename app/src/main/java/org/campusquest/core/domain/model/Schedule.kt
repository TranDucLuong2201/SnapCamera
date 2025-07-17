package org.campusquest.core.domain.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by Duclu 
 * Email: ducluongtran597@gmail.com
 */

data class Schedule(
    val id: String,
    val subject: String,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String,
    val room: String,
    val active: Boolean,
    val semesterId: String,
    val createdAt: Long
) {
    fun isCurrentlyActive(): Boolean {
        val now = Calendar.getInstance()
        val currentDay = now.get(Calendar.DAY_OF_WEEK)
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now.time)

        return dayOfWeek == currentDay &&
                currentTime >= startTime &&
                currentTime <= endTime &&
                active
    }

    fun getTimeRange(): String = "$startTime - $endTime"

    fun getDayName(): String = when (dayOfWeek) {
        1 -> "Chủ nhật"
        2 -> "Thứ 2"
        3 -> "Thứ 3"
        4 -> "Thứ 4"
        5 -> "Thứ 5"
        6 -> "Thứ 6"
        7 -> "Thứ 7"
        else -> ""
    }
}