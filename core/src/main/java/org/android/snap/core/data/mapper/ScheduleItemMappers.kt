package org.android.snap.core.data.mapper

import org.android.snap.core.data.entities.ScheduleItemEntity
import org.android.snap.core.domain.model.ScheduleItem

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// ScheduleItem Mappers
fun ScheduleItemEntity.toDomain(): ScheduleItem {
	return ScheduleItem(
		id = id,
		subjectId = subjectId,
		dayOfWeek = dayOfWeek,
		startTime = startTime,
		endTime = endTime,
		room = room,
		weeks = weeks,
		isActive = isActive,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

fun ScheduleItem.toEntity(): ScheduleItemEntity {
	return ScheduleItemEntity(
		id = id,
		subjectId = subjectId,
		dayOfWeek = dayOfWeek,
		startTime = startTime,
		endTime = endTime,
		room = room,
		weeks = weeks,
		isActive = isActive,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
