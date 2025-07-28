package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.UserPreference.TABLE_NAME
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = TABLE_NAME)
data class UserPreferenceEntity(
	@PrimaryKey val keys: String,
	val value: String,
	val updatedAt: LocalDateTime,
)
