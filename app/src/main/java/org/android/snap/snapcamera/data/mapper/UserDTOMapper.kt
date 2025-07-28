package org.android.snap.snapcamera.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.domain.model.User
import org.android.snap.snapcamera.data.dto.UserDto
import java.time.Instant
import java.time.ZoneId

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// User DTO Mappers
@RequiresApi(Build.VERSION_CODES.O)
fun UserDto.toDomain(): User {
	return User(
		id = id,
		email = email,
		displayName = displayName,
		photoUrl = photoUrl,
		createdAt = Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).toLocalDateTime(),
		updatedAt = Instant.ofEpochMilli(updatedAt).atZone(ZoneId.systemDefault()).toLocalDateTime(),
	)
}

@RequiresApi(Build.VERSION_CODES.O)
fun User.toDto(): UserDto {
	return UserDto(
		id = id,
		email = email,
		displayName = displayName,
		photoUrl = photoUrl.toString(),
		createdAt = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
		updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
	)
}
