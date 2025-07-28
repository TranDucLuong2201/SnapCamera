package org.android.snap.core.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.data.entities.PdfExportEntity
import org.android.snap.core.domain.model.PdfExport

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// PdfExport Mappers
fun PdfExportEntity.toDomain(): PdfExport {
	return PdfExport(
		id = id,
		name = name,
		description = description,
		localPath = filePath,
		firebaseUrl = firebaseUrl,
		imageIds = imageIds,
		sessionIds = sessionIds,
		totalPages = totalPages,
		fileSize = fileSize,
		exportType = exportType,
		isUploaded = isUploaded,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

@RequiresApi(Build.VERSION_CODES.O)
fun PdfExport.toEntity(): PdfExportEntity {
	return PdfExportEntity(
		id = id,
		name = name,
		description = description,
		filePath = localPath,
		firebaseUrl = firebaseUrl,
		imageIds = imageIds,
		sessionIds = sessionIds,
		totalPages = totalPages,
		fileSize = fileSize,
		exportType = exportType,
		isUploaded = isUploaded,
		// TODO: Implement when needed
		uploadedAt = null,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
