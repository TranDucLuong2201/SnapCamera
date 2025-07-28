package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.ScheduleItem.FK_CHILD
import org.android.snap.core.utils.DbConstants.ScheduleItem.FK_PARENT
import org.android.snap.core.utils.DbConstants.ScheduleItem.TABLE_NAME
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(
	tableName = TABLE_NAME,
	foreignKeys = [
		ForeignKey(
			entity = SubjectEntity::class,
			parentColumns = [FK_PARENT],
			childColumns = [FK_CHILD],
			onDelete = ForeignKey.CASCADE,
		),
	],
	indices = [
		Index("subjectId"),
		Index("dayOfWeek"),
		Index("isActive"),
		Index("startTime"),
		Index("endTime"),
		Index("createdAt"),
		Index("updatedAt"),
	],
)
data class ScheduleItemEntity(
	@PrimaryKey val id: String,
	val subjectId: String,
	// 1=Monday, 7=Sunday
	val dayOfWeek: DayOfWeek,
	// HH:mm format
	val startTime: LocalTime,
	// HH:mm format
	val endTime: LocalTime,
	val room: String?,
	// JSON array của các tuần học [1,2,3,4...]
	val weeks: List<Int>,
	val isActive: Boolean = true,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
