package org.campusquest.core.domain.usecase.export_usecase

import org.campusquest.core.domain.repository.FileRepository
import org.campusquest.core.domain.repository.PhotoRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class ExportPhotosUseCase(
    private val photoRepository: PhotoRepository,
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(
        exportType: ExportType,
        identifier: String,
        semesterId: String
    ): Result<String> {
        return try {
            val photos = when (exportType) {
                ExportType.BY_DAY -> photoRepository.getPhotosByDay(identifier)
                ExportType.BY_SUBJECT -> photoRepository.getPhotosBySubject(identifier)
                ExportType.BY_SEMESTER -> photoRepository.getPhotosBySemester(semesterId)
                ExportType.BY_DATE_RANGE -> {
                    val dates = identifier.split(",")
                    photoRepository.getPhotosByDateRange(dates[0], dates[1])
                }
            }

            val fileName = generateFileName(exportType, identifier)
            val zipPath = fileRepository.createZipFile(photos, fileName)
            Result.success(zipPath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateFileName(exportType: ExportType, identifier: String): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return when (exportType) {
            ExportType.BY_DAY -> "Photos_${identifier}_$timestamp.zip"
            ExportType.BY_SUBJECT -> "${identifier}_$timestamp.zip"
            ExportType.BY_SEMESTER -> "Semester_$timestamp.zip"
            ExportType.BY_DATE_RANGE -> "Photos_Range_$timestamp.zip"
        }
    }
}

enum class ExportType {
    BY_DAY, BY_SUBJECT, BY_SEMESTER, BY_DATE_RANGE
}