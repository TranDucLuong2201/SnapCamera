package org.campusquest.core.domain.model

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class Photo(
    val id: String,
    val path: String,
    val subject: String,
    val takenAt: Long,
    val day: String,
    val note: String,
    val scheduleId: String,
    val semesterId: String,
    val fileSize: Long,
    val isUploaded: Boolean
) {
    fun getFormattedDate(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(takenAt))

    fun getFormattedTime(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(takenAt))

    fun getFileSizeFormatted(): String {
        val sizeInMB = fileSize / (1024 * 1024)
        return if (sizeInMB < 1) {
            "${fileSize / 1024} KB"
        } else {
            "$sizeInMB MB"
        }
    }

    fun exists(): Boolean = File(path).exists()
}
