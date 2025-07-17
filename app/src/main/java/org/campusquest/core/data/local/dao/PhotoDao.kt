package org.campusquest.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.campusquest.core.data.local.entities.PhotoEntity

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos WHERE day = :day ORDER BY takenAt DESC")
    suspend fun getPhotosByDay(day: String): List<PhotoEntity>

    @Query("SELECT * FROM photos WHERE subject = :subject ORDER BY takenAt DESC")
    suspend fun getPhotosBySubject(subject: String): List<PhotoEntity>

    @Query("SELECT * FROM photos WHERE semesterId = :semesterId ORDER BY takenAt DESC")
    suspend fun getPhotosBySemester(semesterId: String): List<PhotoEntity>

    @Query("SELECT * FROM photos WHERE day BETWEEN :startDate AND :endDate ORDER BY takenAt DESC")
    suspend fun getPhotosByDateRange(startDate: String, endDate: String): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity)

    @Delete
    suspend fun deletePhoto(photo: PhotoEntity)

    @Query("DELETE FROM photos WHERE id IN (:photoIds)")
    suspend fun deletePhotos(photoIds: List<String>)

    @Query("SELECT DISTINCT day FROM photos WHERE semesterId = :semesterId ORDER BY day DESC")
    suspend fun getPhotosDays(semesterId: String): List<String>

    @Query("SELECT COUNT(*) FROM photos WHERE subject = :subject AND semesterId = :semesterId")
    suspend fun getPhotoCountBySubject(subject: String, semesterId: String): Int

    @Query("SELECT SUM(fileSize) FROM photos WHERE semesterId = :semesterId")
    suspend fun getTotalStorageSize(semesterId: String): Long

    @Query("UPDATE photos SET isUploaded = :isUploaded WHERE id = :photoId")
    suspend fun updateUploadStatus(photoId: String, isUploaded: Boolean)
}