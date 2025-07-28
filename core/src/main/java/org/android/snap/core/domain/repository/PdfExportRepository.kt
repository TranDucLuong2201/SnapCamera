package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.common.UploadProgress
import org.android.snap.core.domain.model.PdfExport
import org.android.snap.core.utils.ExportType

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface PdfExportRepository {
	fun getAllExports(): Flow<Result<List<PdfExport>>>
	fun getExportsByType(type: ExportType): Flow<Result<List<PdfExport>>>
	suspend fun getExportById(id: String): Result<PdfExport?>
	suspend fun createPdfFromSession(sessionId: String, name: String, description: String?): Result<PdfExport>
	suspend fun createPdfFromSessions(sessionIds: List<String>, name: String, description: String?): Result<PdfExport>
	suspend fun createCustomPdf(imageIds: List<String>, name: String, description: String?): Result<PdfExport>
	suspend fun updateExport(export: PdfExport): Result<Unit>
	suspend fun deleteExport(exportId: String): Result<Unit>
	suspend fun uploadPdf(exportId: String): Result<UploadProgress>
	suspend fun syncExports(): Result<SyncResult>
}
