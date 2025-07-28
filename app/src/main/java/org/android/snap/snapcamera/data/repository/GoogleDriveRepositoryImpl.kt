package org.android.snap.snapcamera.data.repository

import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.repository.GoogleDriveRepository
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class GoogleDriveRepositoryImpl @Inject constructor() : GoogleDriveRepository {
	override suspend fun uploadToGoogleDrive(
		filePath: String,
		fileName: String,
		mimeType: String,
	): Result<String> {
		TODO("Not yet implemented")
	}

	override suspend fun createFolderStructure(
		semesterName: String,
		subjectName: String,
	): Result<String> {
		TODO("Not yet implemented")
	}

	override suspend fun syncSessionToGoogleDrive(sessionId: String): Result<Unit> {
		TODO("Not yet implemented")
	}

	override suspend fun syncAllToGoogleDrive(): Result<SyncResult> {
		TODO("Not yet implemented")
	}

	override suspend fun isGoogleDriveConnected(): Result<Boolean> {
		TODO("Not yet implemented")
	}

	override suspend fun connectGoogleDrive(): Result<Unit> {
		TODO("Not yet implemented")
	}

	override suspend fun disconnectGoogleDrive(): Result<Unit> {
		TODO("Not yet implemented")
	}
}
