package org.android.snap.core.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.data.entities.StudySessionEntity
import org.android.snap.core.domain.model.StudySession

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// StudySession Mappers
fun StudySessionEntity.toDomain(): StudySession {
	return StudySession(
		id = id,
		subjectId = subjectId,
		date = date,
		sessionNumber = sessionNumber,
		title = title,
		description = description,
		isLiveMode = isLiveMode,
		startTime = startTime,
		endTime = endTime,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

@RequiresApi(Build.VERSION_CODES.O)
fun StudySession.toEntity(): StudySessionEntity {
	return StudySessionEntity(
		id = id,
		subjectId = subjectId,
		date = date,
		sessionNumber = sessionNumber,
		title = title,
		description = description,
		isLiveMode = isLiveMode,
		startTime = startTime,
		endTime = endTime,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
