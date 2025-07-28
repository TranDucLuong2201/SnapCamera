package org.android.snap.core.data.mapper

import org.android.snap.core.data.entities.SemesterEntity
import org.android.snap.core.domain.model.Semester

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// Semester Mappers
fun SemesterEntity.toDomain(): Semester {
	return Semester(
		id = id,
		name = name,
		startDate = startDate,
		endDate = endDate,
		isActive = isActive,
		// Will be populated separately
		subjects = emptyList(),
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

fun Semester.toEntity(): SemesterEntity {
	return SemesterEntity(
		id = id,
		name = name,
		startDate = startDate,
		endDate = endDate,
		isActive = isActive,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
