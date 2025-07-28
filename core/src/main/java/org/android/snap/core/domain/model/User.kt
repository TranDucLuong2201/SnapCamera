package org.android.snap.core.domain.model

import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */
data class User(
	val id: String,
	val email: String,
	val displayName: String,
	val photoUrl: String?,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
