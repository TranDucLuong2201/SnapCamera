package org.android.snap.core.data.mapper

import org.android.snap.core.data.entities.LectureImageEntity
import org.android.snap.core.domain.model.LectureImage

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// LectureImage Mappers
fun LectureImageEntity.toDomain(): LectureImage {
	return LectureImage(
		id = id,
		sessionId = sessionId,
		fileName = fileName,
		localPath = filePath,
		firebaseUrl = firebaseUrl,
		thumbnailPath = thumbnailPath,
		orderIndex = orderIndex,
		capturedAt = capturedAt,
		width = width,
		height = height,
		fileSize = fileSize,
		isUploaded = isUploaded,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

fun LectureImage.toEntity(): LectureImageEntity {
	return LectureImageEntity(
		id = id,
		sessionId = sessionId,
		fileName = fileName,
		filePath = localPath,
		firebaseUrl = firebaseUrl,
		thumbnailPath = thumbnailPath,
		orderIndex = orderIndex,
		capturedAt = capturedAt,
		width = width,
		height = height,
		fileSize = fileSize,
		isUploaded = isUploaded,
		uploadedAt = if (isUploaded) updatedAt else null,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
