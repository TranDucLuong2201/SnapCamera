package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.common.UploadProgress
import org.android.snap.core.domain.model.PdfExport
import org.android.snap.core.utils.ExportType
import org.android.snap.snapcamera.data.repository.PdfExportRepositoryImpl
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class PdfExportUseCase @Inject constructor(
	private val pdfExportRepository: PdfExportRepositoryImpl,
) {

	fun getAllExports(): Flow<Result<List<PdfExport>>> =
		pdfExportRepository.getAllExports()

	fun getExportsByType(type: ExportType): Flow<Result<List<PdfExport>>> =
		pdfExportRepository.getExportsByType(type)

	suspend fun getExportById(id: String): Result<PdfExport?> =
		pdfExportRepository.getExportById(id)

	suspend fun createPdfFromSession(
		sessionId: String,
		name: String,
		description: String?,
	): Result<PdfExport> =
		pdfExportRepository.createPdfFromSession(sessionId, name, description)

	suspend fun createPdfFromSessions(
		sessionIds: List<String>,
		name: String,
		description: String?,
	): Result<PdfExport> =
		pdfExportRepository.createPdfFromSessions(sessionIds, name, description)

	suspend fun createCustomPdf(
		imageIds: List<String>,
		name: String,
		description: String?,
	): Result<PdfExport> =
		pdfExportRepository.createCustomPdf(imageIds, name, description)

	suspend fun updateExport(export: PdfExport): Result<Unit> =
		pdfExportRepository.updateExport(export)

	suspend fun deleteExport(exportId: String): Result<Unit> =
		pdfExportRepository.deleteExport(exportId)

	suspend fun uploadPdf(exportId: String): Result<UploadProgress> =
		pdfExportRepository.uploadPdf(exportId)

	suspend fun syncExports(): Result<SyncResult> =
		pdfExportRepository.syncExports()
}
