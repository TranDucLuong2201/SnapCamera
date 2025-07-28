package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.entities.PdfExportEntity
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface PdfExportDao {
	@Query("SELECT * FROM pdf_exports ORDER BY createdAt DESC")
	fun getAllExports(): Flow<List<PdfExportEntity>>

	@Query("SELECT * FROM pdf_exports WHERE exportType = :type ORDER BY createdAt DESC")
	fun getExportsByType(type: String): Flow<List<PdfExportEntity>>

	@Query("SELECT * FROM pdf_exports WHERE id = :id")
	suspend fun getExportById(id: String): PdfExportEntity?

	@Query("SELECT * FROM pdf_exports WHERE isUploaded = 0")
	suspend fun getUnuploadedExports(): List<PdfExportEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertExport(export: PdfExportEntity)

	@Update
	suspend fun updateExport(export: PdfExportEntity)

	@Query("UPDATE pdf_exports SET isUploaded = 1, uploadedAt = :uploadedAt, firebaseUrl = :url WHERE id = :id")
	suspend fun markAsUploaded(id: String, url: String, uploadedAt: LocalDateTime)

	@Delete
	suspend fun deleteExport(export: PdfExportEntity)
}
