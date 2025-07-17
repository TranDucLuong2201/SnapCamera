package org.campusquest.core.domain.repository

import android.content.Context
import android.net.Uri
import org.campusquest.core.domain.model.Photo

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface FileRepository {
    suspend fun savePhoto(uri: Uri, subject: String, day: String): String
    suspend fun deletePhoto(path: String): Boolean
    suspend fun createZipFile(photos: List<Photo>, fileName: String): String
    suspend fun shareFile(path: String, context: Context)
    suspend fun getFileSize(path: String): Long
    suspend fun createPhotoDirectory(subject: String, day: String): String
}
