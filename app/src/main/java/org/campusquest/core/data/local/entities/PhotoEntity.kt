package org.campusquest.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val path: String,
    val subject: String,
    val takenAt: Long,
    val day: String, // yyyy-MM-dd format
    val note: String = "",
    val scheduleId: String,
    val semesterId: String,
    val fileSize: Long = 0L,
    val isUploaded: Boolean = false
)