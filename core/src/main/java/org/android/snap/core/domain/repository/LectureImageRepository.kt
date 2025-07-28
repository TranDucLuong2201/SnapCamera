package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.common.UploadProgress
import org.android.snap.core.domain.model.LectureImage

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface LectureImageRepository {
	fun getImagesBySession(sessionId: String): Flow<Result<List<LectureImage>>>
	suspend fun getImageById(id: String): Result<LectureImage?>
	suspend fun saveImage(image: LectureImage): Result<LectureImage>
	suspend fun updateImage(image: LectureImage): Result<Unit>
	suspend fun deleteImage(imageId: String): Result<Unit>
	suspend fun reorderImages(sessionId: String, imageIds: List<String>): Result<Unit>
	suspend fun uploadImage(imageId: String): Result<UploadProgress>
	suspend fun syncImages(): Result<SyncResult>
	suspend fun getUnuploadedImages(): Result<List<LectureImage>>
}
