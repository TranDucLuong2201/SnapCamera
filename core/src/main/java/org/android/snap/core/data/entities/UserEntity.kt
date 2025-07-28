package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.User.TABLE_NAME
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = TABLE_NAME)
data class UserEntity(
	@PrimaryKey val id: String,
	val email: String,
	val displayName: String,
	val photoUrl: String?,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
