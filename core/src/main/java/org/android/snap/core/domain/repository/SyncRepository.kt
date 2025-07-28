package org.android.snap.core.domain.repository

import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.entities.SyncStatusEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface SyncRepository {
	suspend fun syncAll(): Result<SyncResult>
	suspend fun syncEntity(entityId: String, entityType: String): Result<Unit>
	suspend fun markEntityForSync(entityId: String, entityType: String): Result<Unit>
	suspend fun getUnSyncedItems(): Result<List<SyncStatusEntity>>
	suspend fun clearSyncErrors(): Result<Unit>
	suspend fun syncUser(userId: String): Result<Unit> // Add user sync
	suspend fun syncSubscription(userId: String): Result<Unit> // Add subscription sync
}
