package org.android.snap.core.common

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

sealed class Result<out T> {
	data class Success<T>(val data: T) : Result<T>()
	data class Error(val exception: Throwable) : Result<Nothing>()
	object Loading : Result<Nothing>()
}
data class SyncResult(
	val isSuccess: Boolean,
	val syncedCount: Int,
	val failedCount: Int,
	val errors: List<String> = emptyList(),
)

data class UploadProgress(
	val entityId: String,
	val progress: Float,
	val isComplete: Boolean,
	val error: String? = null,
)
