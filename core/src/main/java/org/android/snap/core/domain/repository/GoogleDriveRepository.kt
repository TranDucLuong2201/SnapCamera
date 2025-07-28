package org.android.snap.core.domain.repository

import org.android.snap.core.common.SyncResult

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface GoogleDriveRepository {
	suspend fun uploadToGoogleDrive(filePath: String, fileName: String, mimeType: String): Result<String>
	suspend fun createFolderStructure(semesterName: String, subjectName: String): Result<String>
	suspend fun syncSessionToGoogleDrive(sessionId: String): Result<Unit>
	suspend fun syncAllToGoogleDrive(): Result<SyncResult>
	suspend fun isGoogleDriveConnected(): Result<Boolean>
	suspend fun connectGoogleDrive(): Result<Unit>
	suspend fun disconnectGoogleDrive(): Result<Unit>
}
