package org.android.snap.core.domain.model

import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class LectureImage(
	val id: String,
	val sessionId: String,
	val fileName: String,
	val localPath: String,
	val firebaseUrl: String?,
	val thumbnailPath: String?,
	val orderIndex: Int,
	val capturedAt: LocalDateTime,
	val width: Int?,
	val height: Int?,
	val fileSize: Long?,
	val isUploaded: Boolean,
	val notes: List<ImageNote> = emptyList(),
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
