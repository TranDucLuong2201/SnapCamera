package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class UserDto(
	val id: String = "",
	val email: String = "",
	val displayName: String = "",
	val photoUrl: String = "",
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
