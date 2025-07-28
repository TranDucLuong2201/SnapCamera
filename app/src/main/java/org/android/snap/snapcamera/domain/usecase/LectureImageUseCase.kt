package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.common.UploadProgress
import org.android.snap.core.domain.model.LectureImage
import org.android.snap.snapcamera.data.repository.LectureImageRepositoryImpl
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class LectureImageUseCase @Inject constructor(
	private val lectureImageRepository: LectureImageRepositoryImpl,
) {

	fun getImagesBySession(sessionId: String): Flow<Result<List<LectureImage>>> =
		lectureImageRepository.getImagesBySession(sessionId)

	suspend fun getImageById(id: String): Result<LectureImage?> =
		lectureImageRepository.getImageById(id)

	suspend fun saveImage(image: LectureImage): Result<LectureImage> =
		lectureImageRepository.saveImage(image)

	suspend fun updateImage(image: LectureImage): Result<Unit> =
		lectureImageRepository.updateImage(image)

	suspend fun deleteImage(imageId: String): Result<Unit> =
		lectureImageRepository.deleteImage(imageId)

	suspend fun reorderImages(
		sessionId: String,
		imageIds: List<String>,
	): Result<Unit> =
		lectureImageRepository.reorderImages(sessionId, imageIds)

	suspend fun uploadImage(imageId: String): Result<UploadProgress> =
		lectureImageRepository.uploadImage(imageId)

	suspend fun syncImages(): Result<SyncResult> =
		lectureImageRepository.syncImages()

	suspend fun getUnuploadedImages(): Result<List<LectureImage>> =
		lectureImageRepository.getUnuploadedImages()
}
