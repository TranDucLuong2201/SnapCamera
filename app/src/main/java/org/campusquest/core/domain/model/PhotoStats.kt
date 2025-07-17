package org.campusquest.core.domain.model

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class PhotoStats(
    val totalPhotos: Int,
    val totalStorage: Long,
    val photosBySubject: Map<String, Int>,
    val photosByDay: Map<String, Int>,
    val uploadedPhotos: Int
) {
    fun getStorageFormatted(): String {
        val sizeInMB = totalStorage / (1024 * 1024)
        return if (sizeInMB < 1) {
            "${totalStorage / 1024} KB"
        } else {
            "$sizeInMB MB"
        }
    }

    fun getUploadProgress(): Float =
        if (totalPhotos > 0) uploadedPhotos.toFloat() / totalPhotos else 0f
}
