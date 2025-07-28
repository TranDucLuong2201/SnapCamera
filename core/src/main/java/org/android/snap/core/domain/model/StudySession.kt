package org.android.snap.core.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class StudySession(
	val id: String,
	val subjectId: String,
	val date: LocalDate,
	val sessionNumber: Int,
	val title: String?,
	val description: String?,
	val isLiveMode: Boolean,
	val startTime: LocalDateTime?,
	val endTime: LocalDateTime?,
	val images: List<LectureImage> = emptyList(),
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
