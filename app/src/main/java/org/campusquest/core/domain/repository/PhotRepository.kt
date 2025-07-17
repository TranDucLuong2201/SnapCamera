package org.campusquest.core.domain.repository

import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.model.PhotoStats

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface PhotoRepository {
    suspend fun getPhotosByDay(day: String): List<Photo>
    suspend fun getPhotosBySubject(subject: String): List<Photo>
    suspend fun getPhotosBySemester(semesterId: String): List<Photo>
    suspend fun getPhotosByDateRange(startDate: String, endDate: String): List<Photo>
    suspend fun insertPhoto(photo: Photo)
    suspend fun deletePhoto(photo: Photo)
    suspend fun deletePhotos(photoIds: List<String>)
    suspend fun getPhotosDays(semesterId: String): List<String>
    suspend fun getPhotoStats(semesterId: String): PhotoStats
    suspend fun updateUploadStatus(photoId: String, isUploaded: Boolean)
}