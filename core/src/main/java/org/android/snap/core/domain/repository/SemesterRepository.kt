package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.Semester

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface SemesterRepository {
	fun getAllSemesters(): Flow<Result<List<Semester>>>
	fun getActiveSemesters(): Flow<Result<List<Semester>>>
	suspend fun getSemesterById(id: String): Result<Semester?>
	suspend fun createSemester(semester: Semester): Result<Unit>
	suspend fun updateSemester(semester: Semester): Result<Unit>
	suspend fun deleteSemester(semesterId: String): Result<Unit>
	suspend fun syncSemesters(): Result<SyncResult>
}
