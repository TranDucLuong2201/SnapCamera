package org.campusquest.core.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.repository.FileRepository
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class FileRepositoryImpl(
    private val context: Context
) : FileRepository {

    private val baseDir = File(context.getExternalFilesDir(null), "MyAppPhotos")

    init {
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
    }

    override suspend fun savePhoto(uri: Uri, subject: String, day: String): String {
        return withContext(Dispatchers.IO) {
            val subjectDir = createPhotoDirectory(subject, day)
            val fileName = "${System.currentTimeMillis()}.jpg"
            val file = File(subjectDir, fileName)

            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            file.absolutePath
        }
    }

    override suspend fun deletePhoto(path: String): Boolean {
        return withContext(Dispatchers.IO) {
            val file = File(path)
            file.exists() && file.delete()
        }
    }

    override suspend fun createZipFile(photos: List<Photo>, fileName: String): String {
        return withContext(Dispatchers.IO) {
            val zipFile = File(baseDir, fileName)
            val zipOutputStream = ZipOutputStream(FileOutputStream(zipFile))

            photos.forEach { photo ->
                val file = File(photo.path)
                if (file.exists()) {
                    val entry = ZipEntry("${photo.subject}/${photo.day}/${file.name}")
                    zipOutputStream.putNextEntry(entry)
                    file.inputStream().use { input ->
                        input.copyTo(zipOutputStream)
                    }
                    zipOutputStream.closeEntry()
                }
            }

            zipOutputStream.close()
            zipFile.absolutePath
        }
    }

    override suspend fun shareFile(path: String, context: Context) {
        withContext(Dispatchers.Main) {
            val file = File(path)
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = if (file.extension == "zip") "application/zip" else "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Chia sẻ file"))
        }
    }

    override suspend fun getFileSize(path: String): Long {
        return withContext(Dispatchers.IO) {
            File(path).length()
        }
    }

    override suspend fun createPhotoDirectory(subject: String, day: String): String {
        return withContext(Dispatchers.IO) {
            val dayDir = File(baseDir, day)
            val subjectDir = File(dayDir, subject)

            if (!subjectDir.exists()) {
                subjectDir.mkdirs()
            }

            subjectDir.absolutePath
        }
    }
}