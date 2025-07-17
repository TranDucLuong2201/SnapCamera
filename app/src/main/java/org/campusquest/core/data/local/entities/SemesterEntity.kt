package org.campusquest.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = "semesters")
data class SemesterEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val active: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
