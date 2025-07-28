package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class SemesterDto(
	val id: String = "",
	val name: String = "",
	val startDate: Long = 0L,
	val endDate: Long = 0L,
	val isActive: Boolean = true,
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
