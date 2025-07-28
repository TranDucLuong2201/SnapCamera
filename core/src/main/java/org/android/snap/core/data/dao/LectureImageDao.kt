package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.database.relation.LectureImageWithNotes
import org.android.snap.core.data.entities.LectureImageEntity
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface LectureImageDao {
	@Query("SELECT * FROM lecture_images WHERE sessionId = :sessionId ORDER BY orderIndex ASC")
	fun getImagesBySession(sessionId: String): Flow<List<LectureImageEntity>>

	// Pagination Queries
	@Query("SELECT * FROM lecture_images WHERE sessionId = :sessionId ORDER BY orderIndex ASC LIMIT :limit OFFSET :offset")
	suspend fun getImagesBySessionPaginated(sessionId: String, limit: Int, offset: Int): List<LectureImageEntity>

	@Query("SELECT * FROM lecture_images ORDER BY capturedAt DESC LIMIT :limit OFFSET :offset")
	suspend fun getRecentImagesPaginated(limit: Int, offset: Int): List<LectureImageEntity>

	@Query("SELECT * FROM lecture_images WHERE capturedAt BETWEEN :startDate AND :endDate ORDER BY capturedAt DESC LIMIT :limit OFFSET :offset")
	suspend fun getImagesByDateRangePaginated(startDate: LocalDateTime, endDate: LocalDateTime, limit: Int, offset: Int): List<LectureImageEntity>

	@Query("SELECT * FROM lecture_images WHERE id = :id")
	suspend fun getImageById(id: String): LectureImageEntity?

	@Transaction
	@Query("SELECT * FROM lecture_images WHERE id = :id")
	suspend fun getImageWithNotes(id: String): LectureImageWithNotes?

	@Query("SELECT * FROM lecture_images WHERE isUploaded = 0")
	suspend fun getUnuploadedImages(): List<LectureImageEntity>

	@Query("SELECT COUNT(*) FROM lecture_images WHERE sessionId = :sessionId")
	suspend fun getImageCountBySession(sessionId: String): Int

	// Statistics Queries
	@Query("SELECT COUNT(*) FROM lecture_images")
	suspend fun getTotalImageCount(): Int

	@Query("SELECT COUNT(*) FROM lecture_images WHERE isUploaded = 1")
	suspend fun getUploadedImageCount(): Int

	@Query("SELECT COUNT(*) FROM lecture_images WHERE isUploaded = 0")
	suspend fun getUnuploadedImageCount(): Int

	@Query("SELECT SUM(fileSize) FROM lecture_images WHERE fileSize IS NOT NULL")
	suspend fun getTotalStorageUsed(): Long?

	@Query("SELECT AVG(fileSize) FROM lecture_images WHERE fileSize IS NOT NULL")
	suspend fun getAverageImageSize(): Double?

	// Date-based Queries
	@Query("SELECT * FROM lecture_images WHERE DATE(capturedAt) = :date ORDER BY capturedAt DESC")
	suspend fun getImagesByDate(date: LocalDate): List<LectureImageEntity>

	@Query("SELECT * FROM lecture_images WHERE capturedAt >= :fromDate ORDER BY capturedAt DESC")
	fun getImagesFromDate(fromDate: LocalDateTime): Flow<List<LectureImageEntity>>

	// Search Queries
	@Query("SELECT * FROM lecture_images WHERE fileName LIKE '%' || :query || '%' ORDER BY capturedAt DESC")
	suspend fun searchImagesByFileName(query: String): List<LectureImageEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertImage(image: LectureImageEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertImages(images: List<LectureImageEntity>)

	@Update
	suspend fun updateImage(image: LectureImageEntity)

	@Query("UPDATE lecture_images SET isUploaded = 1, uploadedAt = :uploadedAt, firebaseUrl = :url WHERE id = :id")
	suspend fun markAsUploaded(id: String, url: String, uploadedAt: LocalDateTime)

	@Query("UPDATE lecture_images SET orderIndex = :newIndex WHERE id = :id")
	suspend fun updateOrderIndex(id: String, newIndex: Int)

	@Delete
	suspend fun deleteImage(image: LectureImageEntity)

	@Query("DELETE FROM lecture_images WHERE sessionId = :sessionId")
	suspend fun deleteImagesBySession(sessionId: String)

	@Query("DELETE FROM lecture_images WHERE capturedAt < :beforeDate")
	suspend fun deleteOldImages(beforeDate: LocalDateTime)
}
