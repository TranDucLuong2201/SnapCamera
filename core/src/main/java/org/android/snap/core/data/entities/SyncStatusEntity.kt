package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.SyncStatus.TABLE_NAME

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = TABLE_NAME)
data class SyncStatusEntity(
	@PrimaryKey val entityId: String,
	// "semester", "subject", "session", "image", "pdf", "user", "subscription"
	val entityType: String,
	val lastSyncAt: Long?,
	val needsSync: Boolean = true,
	val syncError: String? = null,
	val retryCount: Int = 0,
	val updatedAt: Long = System.currentTimeMillis(),
)
