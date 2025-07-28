package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class LectureImageDto(
	val id: String = "",
	val sessionId: String = "",
	val fileName: String = "",
	val firebaseUrl: String? = null,
	val thumbnailUrl: String? = null,
	val orderIndex: Int = 0,
	val capturedAt: Long = 0L,
	val width: Int? = null,
	val height: Int? = null,
	val fileSize: Long? = null,
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
