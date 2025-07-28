package org.android.snap.core.domain.model

import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class Subject(
	val id: String,
	val semesterId: String,
	val name: String,
	val code: String?,
	val color: String?,
	val teacher: String?,
	val room: String?,
	val credits: Int?,
	val isCompleted: Boolean,
	val completedAt: LocalDateTime?,
	val scheduleItems: List<ScheduleItem> = emptyList(),
	val studySessions: List<StudySession> = emptyList(),
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
