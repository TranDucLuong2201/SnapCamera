package org.android.snap.core.domain.model

import org.android.snap.core.utils.ExportType
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class PdfExport(
	val id: String,
	val name: String,
	val description: String?,
	val localPath: String,
	val firebaseUrl: String?,
	val imageIds: List<String>,
	val sessionIds: List<String>,
	val totalPages: Int,
	val fileSize: Long,
	val exportType: ExportType,
	val isUploaded: Boolean,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
