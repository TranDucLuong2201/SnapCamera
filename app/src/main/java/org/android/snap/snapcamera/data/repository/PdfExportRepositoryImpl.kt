package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.common.UploadProgress
import org.android.snap.core.data.dao.PdfExportDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.PdfExport
import org.android.snap.core.domain.repository.PdfExportRepository
import org.android.snap.core.utils.ExportType
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class PdfExportRepositoryImpl @Inject constructor(
	private val pdfExportDao: PdfExportDao,
) : PdfExportRepository {

	override fun getAllExports(): Flow<Result<List<PdfExport>>> =
		pdfExportDao.getAllExports().map { exportEntities ->
			Result.success(exportEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override fun getExportsByType(type: ExportType): Flow<Result<List<PdfExport>>> =
		pdfExportDao.getExportsByType(type.name).map { exportEntities ->
			Result.success(exportEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getExportById(id: String): Result<PdfExport?> =
		try {
			val export = pdfExportDao.getExportById(id)
			Result.success(export?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createPdfFromSession(
		sessionId: String,
		name: String,
		description: String?,
	): Result<PdfExport> =
		try {
			val export = PdfExport(
				id = generateExportId(),
				name = name,
				description = description,
				localPath = "",
				firebaseUrl = null,
				imageIds = emptyList(),
				sessionIds = listOf(sessionId),
				totalPages = 0,
				fileSize = 0L,
				exportType = ExportType.SESSION,
				isUploaded = false,
				createdAt = LocalDateTime.now(),
				updatedAt = LocalDateTime.now(),
			)
			val exportEntity = export.toEntity()
			pdfExportDao.insertExport(exportEntity)
			Result.success(export)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createPdfFromSessions(
		sessionIds: List<String>,
		name: String,
		description: String?,
	): Result<PdfExport> =
		try {
			val export = PdfExport(
				id = generateExportId(),
				name = name,
				description = description,
				localPath = "",
				firebaseUrl = null,
				imageIds = emptyList(),
				sessionIds = sessionIds,
				totalPages = 0,
				fileSize = 0L,
				exportType = ExportType.CUSTOM,
				isUploaded = false,
				createdAt = LocalDateTime.now(),
				updatedAt = LocalDateTime.now(),
			)
			val exportEntity = export.toEntity()
			pdfExportDao.insertExport(exportEntity)
			Result.success(export)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createCustomPdf(
		imageIds: List<String>,
		name: String,
		description: String?,
	): Result<PdfExport> =
		try {
			val export = PdfExport(
				id = generateExportId(),
				name = name,
				description = description,
				localPath = "",
				firebaseUrl = null,
				imageIds = imageIds,
				sessionIds = emptyList(),
				totalPages = 0,
				fileSize = 0L,
				exportType = ExportType.CUSTOM,
				isUploaded = false,
				createdAt = LocalDateTime.now(),
				updatedAt = LocalDateTime.now(),
			)
			val exportEntity = export.toEntity()
			pdfExportDao.insertExport(exportEntity)
			Result.success(export)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateExport(export: PdfExport): Result<Unit> =
		try {
			val exportEntity = export.toEntity()
			pdfExportDao.updateExport(exportEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteExport(exportId: String): Result<Unit> =
		try {
			val export = pdfExportDao.getExportById(exportId)
			export?.let { pdfExportDao.deleteExport(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun uploadPdf(exportId: String): Result<UploadProgress> =
		try {
			// TODO: Implement Firebase Storage upload logic
			val export = pdfExportDao.getExportById(exportId)
			if (export != null) {
				// Simulate upload progress
				val fakeUrl = "https://firebasestorage.googleapis.com/fake-pdf-url/$exportId"
				pdfExportDao.markAsUploaded(exportId, fakeUrl, LocalDateTime.now())
				Result.success(UploadProgress(exportId, 1.0f, true))
			} else {
				Result.failure(Exception("Export not found"))
			}
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncExports(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	private fun generateExportId(): String = "pdf_export_${System.currentTimeMillis()}"
}
