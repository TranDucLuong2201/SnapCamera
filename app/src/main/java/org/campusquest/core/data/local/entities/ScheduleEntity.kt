package org.campusquest.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val subject: String,
    val dayOfWeek: Int, // 1-7 (Monday-Sunday)
    val startTime: String, // HH:mm format
    val endTime: String,
    val room: String = "",
    val active: Boolean = true,
    val semesterId: String,
    val createdAt: Long = System.currentTimeMillis()
)