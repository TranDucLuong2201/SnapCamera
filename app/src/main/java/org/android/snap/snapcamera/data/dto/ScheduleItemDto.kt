package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class ScheduleItemDto(
	val id: String = "",
	val subjectId: String = "",
	val dayOfWeek: Int = 0,
	val startTime: String = "",
	val endTime: String = "",
	val room: String? = null,
	val weeks: List<Int> = emptyList(),
	val isActive: Boolean = true,
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
