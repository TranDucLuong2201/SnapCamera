package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.entities.SyncStatusEntity
import org.android.snap.snapcamera.data.repository.SyncRepositoryImpl
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SyncUseCase @Inject constructor(
	private val syncRepository: SyncRepositoryImpl,
) {

	suspend fun syncAll(): Result<SyncResult> =
		syncRepository.syncAll()

	suspend fun syncEntity(
		entityId: String,
		entityType: String,
	): Result<Unit> =
		syncRepository.syncEntity(entityId, entityType)

	suspend fun markEntityForSync(
		entityId: String,
		entityType: String,
	): Result<Unit> =
		syncRepository.markEntityForSync(entityId, entityType)

	suspend fun getUnSyncedItems(): Result<List<SyncStatusEntity>> =
		syncRepository.getUnSyncedItems()

	suspend fun clearSyncErrors(): Result<Unit> =
		syncRepository.clearSyncErrors()

	suspend fun syncUser(userId: String): Result<Unit> =
		syncRepository.syncUser(userId)

	suspend fun syncSubscription(userId: String): Result<Unit> =
		syncRepository.syncSubscription(userId)
}
