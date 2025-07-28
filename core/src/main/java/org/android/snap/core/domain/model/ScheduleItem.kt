package org.android.snap.core.domain.model

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class ScheduleItem(
	val id: String,
	val subjectId: String,
	val dayOfWeek: DayOfWeek,
	val startTime: LocalTime,
	val endTime: LocalTime,
	val room: String?,
	val weeks: List<Int>,
	val isActive: Boolean,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
