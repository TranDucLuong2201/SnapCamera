package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class PdfExportDto(
	val id: String = "",
	val name: String = "",
	val description: String? = null,
	val firebaseUrl: String? = null,
	val imageIds: List<String> = emptyList(),
	val sessionIds: List<String> = emptyList(),
	val totalPages: Int = 0,
	val fileSize: Long = 0L,
	val exportType: String = "",
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
