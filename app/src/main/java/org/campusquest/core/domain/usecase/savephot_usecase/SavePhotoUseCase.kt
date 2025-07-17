package org.campusquest.core.domain.usecase.savephot_usecase

import android.net.Uri
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.repository.FileRepository
import org.campusquest.core.domain.repository.PhotoRepository
import org.campusquest.core.domain.usecase.class_usecase.GetCurrentClassUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class SavePhotoUseCase(
    private val photoRepository: PhotoRepository,
    private val fileRepository: FileRepository,
    private val getCurrentClassUseCase: GetCurrentClassUseCase
) {
    suspend operator fun invoke(uri: Uri, note: String = ""): Result<Photo> {
        return try {
            val currentClass = getCurrentClassUseCase()
            val subject = currentClass.schedule?.subject ?: "Unknown"
            val day = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val savedPath = fileRepository.savePhoto(uri, subject, day)
            val fileSize = fileRepository.getFileSize(savedPath)

            val photo = Photo(
                id = UUID.randomUUID().toString(),
                path = savedPath,
                subject = subject,
                takenAt = System.currentTimeMillis(),
                day = day,
                note = note,
                scheduleId = currentClass.schedule?.id ?: "",
                semesterId = currentClass.schedule?.semesterId ?: "",
                fileSize = fileSize,
                isUploaded = false
            )

            photoRepository.insertPhoto(photo)
            Result.success(photo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}