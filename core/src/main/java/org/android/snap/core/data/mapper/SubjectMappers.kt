package org.android.snap.core.data.mapper

import org.android.snap.core.data.entities.SubjectEntity
import org.android.snap.core.domain.model.Subject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// Subject Mappers
fun SubjectEntity.toDomain(): Subject {
	return Subject(
		id = id,
		semesterId = semesterId,
		name = name,
		code = code,
		color = color,
		teacher = teacher,
		room = room,
		credits = credits,
		isCompleted = isCompleted,
		completedAt = completedAt,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

fun Subject.toEntity(): SubjectEntity {
	return SubjectEntity(
		id = id,
		semesterId = semesterId,
		name = name,
		code = code,
		color = color,
		teacher = teacher,
		room = room,
		credits = credits,
		isCompleted = isCompleted,
		completedAt = completedAt,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
