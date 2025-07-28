package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.common.UploadProgress
import org.android.snap.core.data.dao.LectureImageDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.LectureImage
import org.android.snap.core.domain.repository.LectureImageRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class LectureImageRepositoryImpl @Inject constructor(
	private val lectureImageDao: LectureImageDao,
) : LectureImageRepository {

	override fun getImagesBySession(sessionId: String): Flow<Result<List<LectureImage>>> =
		lectureImageDao.getImagesBySession(sessionId).map { imageEntities ->
			Result.success(imageEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getImageById(id: String): Result<LectureImage?> =
		try {
			val image = lectureImageDao.getImageById(id)
			Result.success(image?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun saveImage(image: LectureImage): Result<LectureImage> =
		try {
			val imageEntity = image.toEntity()
			lectureImageDao.insertImage(imageEntity)
			Result.success(image)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateImage(image: LectureImage): Result<Unit> =
		try {
			val imageEntity = image.toEntity()
			lectureImageDao.updateImage(imageEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteImage(imageId: String): Result<Unit> =
		try {
			val image = lectureImageDao.getImageById(imageId)
			image?.let { lectureImageDao.deleteImage(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun reorderImages(
		sessionId: String,
		imageIds: List<String>,
	): Result<Unit> =
		try {
			imageIds.forEachIndexed { index, imageId ->
				lectureImageDao.updateOrderIndex(imageId, index)
			}
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun uploadImage(imageId: String): Result<UploadProgress> =
		try {
			// TODO: Implement Firebase Storage upload logic
			val image = lectureImageDao.getImageById(imageId)
			if (image != null) {
				// Simulate upload progress
				val fakeUrl = "https://firebasestorage.googleapis.com/fake-url/$imageId"
				lectureImageDao.markAsUploaded(imageId, fakeUrl, LocalDateTime.now())
				Result.success(UploadProgress(imageId, 1.0f, true))
			} else {
				Result.failure(Exception("Image not found"))
			}
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncImages(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun getUnuploadedImages(): Result<List<LectureImage>> =
		try {
			val unuploadedImages = lectureImageDao.getUnuploadedImages()
			Result.success(unuploadedImages.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
