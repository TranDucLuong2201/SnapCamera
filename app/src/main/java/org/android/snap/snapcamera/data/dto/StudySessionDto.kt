package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class StudySessionDto(
	val id: String = "",
	val subjectId: String = "",
	val date: Long = 0L,
	val sessionNumber: Int = 0,
	val title: String? = null,
	val description: String? = null,
	val isLiveMode: Boolean = false,
	val startTime: Long? = null,
	val endTime: Long? = null,
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
