package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.Semester
import org.android.snap.snapcamera.data.repository.SemesterRepositoryImpl
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SemesterUseCase @Inject constructor(
	private val semesterRepository: SemesterRepositoryImpl,
) {

	fun getAllSemesters(): Flow<Result<List<Semester>>> =
		semesterRepository.getAllSemesters()

	fun getActiveSemesters(): Flow<Result<List<Semester>>> =
		semesterRepository.getActiveSemesters()

	suspend fun getSemesterById(id: String): Result<Semester?> =
		semesterRepository.getSemesterById(id)

	suspend fun createSemester(semester: Semester): Result<Unit> =
		semesterRepository.createSemester(semester)

	suspend fun updateSemester(semester: Semester): Result<Unit> =
		semesterRepository.updateSemester(semester)

	suspend fun deleteSemester(semesterId: String): Result<Unit> =
		semesterRepository.deleteSemester(semesterId)

	suspend fun syncSemesters(): Result<SyncResult> =
		semesterRepository.syncSemesters()
}
