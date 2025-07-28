package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class SubjectDto(
	val id: String = "",
	val semesterId: String = "",
	val name: String = "",
	val code: String? = null,
	val color: String? = null,
	val teacher: String? = null,
	val room: String? = null,
	val credits: Int? = null,
	val isCompleted: Boolean = false,
	val completedAt: Long? = null,
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
