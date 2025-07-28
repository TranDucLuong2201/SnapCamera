package org.android.snap.core.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class Semester(
	val id: String,
	val name: String,
	val startDate: LocalDate,
	val endDate: LocalDate,
	val isActive: Boolean,
	val subjects: List<Subject> = emptyList(),
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
