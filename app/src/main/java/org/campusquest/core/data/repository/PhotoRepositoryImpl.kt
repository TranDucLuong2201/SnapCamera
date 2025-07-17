package org.campusquest.core.data.repository

import org.campusquest.core.data.local.dao.PhotoDao
import org.campusquest.core.data.mapper.toDomain
import org.campusquest.core.data.mapper.toEntity
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.model.PhotoStats
import org.campusquest.core.domain.repository.PhotoRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class PhotoRepositoryImpl(
    private val photoDao: PhotoDao
) : PhotoRepository {

    override suspend fun getPhotosByDay(day: String): List<Photo> {
        return photoDao.getPhotosByDay(day).map { it.toDomain() }
    }

    override suspend fun getPhotosBySubject(subject: String): List<Photo> {
        return photoDao.getPhotosBySubject(subject).map { it.toDomain() }
    }

    override suspend fun getPhotosBySemester(semesterId: String): List<Photo> {
        return photoDao.getPhotosBySemester(semesterId).map { it.toDomain() }
    }

    override suspend fun getPhotosByDateRange(startDate: String, endDate: String): List<Photo> {
        return photoDao.getPhotosByDateRange(startDate, endDate).map { it.toDomain() }
    }

    override suspend fun insertPhoto(photo: Photo) {
        photoDao.insertPhoto(photo.toEntity())
    }

    override suspend fun deletePhoto(photo: Photo) {
        photoDao.deletePhoto(photo.toEntity())
    }

    override suspend fun deletePhotos(photoIds: List<String>) {
        photoDao.deletePhotos(photoIds)
    }

    override suspend fun getPhotosDays(semesterId: String): List<String> {
        return photoDao.getPhotosDays(semesterId)
    }

    override suspend fun getPhotoStats(semesterId: String): PhotoStats {
        val photos = photoDao.getPhotosBySemester(semesterId)
        val totalStorage = photoDao.getTotalStorageSize(semesterId)

        val photosBySubject = photos.groupBy { it.subject }
            .mapValues { it.value.size }

        val photosByDay = photos.groupBy { it.day }
            .mapValues { it.value.size }

        val uploadedPhotos = photos.count { it.isUploaded }

        return PhotoStats(
            totalPhotos = photos.size,
            totalStorage = totalStorage,
            photosBySubject = photosBySubject,
            photosByDay = photosByDay,
            uploadedPhotos = uploadedPhotos
        )
    }

    override suspend fun updateUploadStatus(photoId: String, isUploaded: Boolean) {
        photoDao.updateUploadStatus(photoId, isUploaded)
    }
}