package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.android.snap.core.data.entities.SyncStatusEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface SyncStatusDao {
	@Query("SELECT * FROM sync_status WHERE needsSync = 1")
	suspend fun getItemsNeedingSync(): List<SyncStatusEntity>

	@Query("SELECT * FROM sync_status WHERE entityId = :entityId AND entityType = :type")
	suspend fun getSyncStatus(entityId: String, type: String): SyncStatusEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSyncStatus(status: SyncStatusEntity)

	@Query("UPDATE sync_status SET needsSync = 0, lastSyncAt = :syncTime WHERE entityId = :entityId AND entityType = :type")
	suspend fun markAsSynced(entityId: String, type: String, syncTime: Long)

	@Query("UPDATE sync_status SET syncError = :error, retryCount = retryCount + 1 WHERE entityId = :entityId AND entityType = :type")
	suspend fun updateSyncError(entityId: String, type: String, error: String)

	@Delete
	suspend fun deleteSyncStatus(status: SyncStatusEntity)
}
