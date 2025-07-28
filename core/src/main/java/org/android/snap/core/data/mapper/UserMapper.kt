package org.android.snap.core.data.mapper

import org.android.snap.core.data.entities.UserEntity
import org.android.snap.core.domain.model.User

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// User Mappers
fun UserEntity.toDomain(): User {
	return User(
		id = id,
		email = email,
		displayName = displayName,
		photoUrl = photoUrl,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

fun User.toEntity(): UserEntity {
	return UserEntity(
		id = id,
		email = email,
		displayName = displayName,
		photoUrl = photoUrl,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
