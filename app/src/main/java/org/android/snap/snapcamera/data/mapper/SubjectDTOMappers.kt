package org.android.snap.snapcamera.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.domain.model.Subject
import org.android.snap.snapcamera.data.dto.SubjectDto
import java.time.Instant
import java.time.ZoneId

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// Subject DTO Mappers
@RequiresApi(Build.VERSION_CODES.O)
fun SubjectDto.toDomain(): Subject {
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
		completedAt = completedAt?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() },
		createdAt = Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).toLocalDateTime(),
		updatedAt = Instant.ofEpochMilli(updatedAt).atZone(ZoneId.systemDefault()).toLocalDateTime(),
	)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Subject.toDto(): SubjectDto {
	return SubjectDto(
		id = id,
		semesterId = semesterId,
		name = name,
		code = code,
		color = color,
		teacher = teacher,
		room = room,
		credits = credits,
		isCompleted = isCompleted,
		completedAt = completedAt?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli(),
		createdAt = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
		updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
	)
}
