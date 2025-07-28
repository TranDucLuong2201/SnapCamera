package org.android.snap.snapcamera.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.domain.model.Semester
import org.android.snap.snapcamera.data.dto.SemesterDto
import java.time.Instant
import java.time.ZoneId

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// Semester DTO Mappers
@RequiresApi(Build.VERSION_CODES.O)
fun SemesterDto.toDomain(): Semester {
	return Semester(
		id = id,
		name = name,
		startDate = Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDate(),
		endDate = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).toLocalDate(),
		isActive = isActive,
		createdAt = Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).toLocalDateTime(),
		updatedAt = Instant.ofEpochMilli(updatedAt).atZone(ZoneId.systemDefault()).toLocalDateTime(),
	)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Semester.toDto(): SemesterDto {
	return SemesterDto(
		id = id,
		name = name,
		startDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
		endDate = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
		isActive = isActive,
		createdAt = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
		updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
	)
}
