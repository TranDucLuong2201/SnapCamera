package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.Semester.TABLE_NAME
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(
	tableName = TABLE_NAME,
	indices = [
		Index("isActive"),
		Index("startDate"),
		Index("endDate"),
		Index("createdAt"),
	],
)
data class SemesterEntity(
	@PrimaryKey val id: String,
	val name: String,
	val startDate: LocalDate,
	val endDate: LocalDate,
	val isActive: Boolean = true,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
