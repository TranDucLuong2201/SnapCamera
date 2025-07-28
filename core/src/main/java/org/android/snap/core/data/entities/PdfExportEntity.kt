package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.PdfExport.TABLE_NAME
import org.android.snap.core.utils.ExportType
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = TABLE_NAME)
data class PdfExportEntity(
	@PrimaryKey val id: String,
	val name: String,
	val description: String?,
	val filePath: String,
	val firebaseUrl: String?,
	// JSON array của image IDs
	val imageIds: List<String>,
	// JSON array của session IDs
	val sessionIds: List<String>,
	val totalPages: Int,
	val fileSize: Long,
	// "session", "subject", "date_range", "custom"
	val exportType: ExportType,
	val isUploaded: Boolean = false,
	val uploadedAt: LocalDateTime? = null,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
