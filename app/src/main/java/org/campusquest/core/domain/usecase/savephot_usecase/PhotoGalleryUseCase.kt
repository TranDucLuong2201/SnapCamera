package org.campusquest.core.domain.usecase.savephot_usecase

import android.content.Context
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.repository.FileRepository
import org.campusquest.core.domain.repository.PhotoRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class PhotoGalleryUseCase(
    private val photoRepository: PhotoRepository,
    private val fileRepository: FileRepository
) {
    suspend fun getPhotosByDay(day: String): List<Photo> {
        return photoRepository.getPhotosByDay(day)
    }

    suspend fun getPhotosBySubject(subject: String): List<Photo> {
        return photoRepository.getPhotosBySubject(subject)
    }

    suspend fun getTodayPhotos(): List<Photo> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return photoRepository.getPhotosByDay(today)
    }

    suspend fun deletePhotos(photoIds: List<String>): Result<Unit> {
        return try {
            photoRepository.deletePhotos(photoIds)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sharePhotos(photos: List<Photo>, context: Context): Result<Unit> {
        return try {
            val zipFileName = "Photos_${System.currentTimeMillis()}.zip"
            val zipPath = fileRepository.createZipFile(photos, zipFileName)
            fileRepository.shareFile(zipPath, context)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}