package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.dao.SemesterDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.Semester
import org.android.snap.core.domain.repository.SemesterRepository
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SemesterRepositoryImpl @Inject constructor(
	private val semesterDao: SemesterDao,
) : SemesterRepository {

	override fun getAllSemesters(): Flow<Result<List<Semester>>> =
		semesterDao.getAllSemesters().map { semesterEntities ->
			Result.success(semesterEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override fun getActiveSemesters(): Flow<Result<List<Semester>>> =
		semesterDao.getActiveSemesters().map { semesterEntities ->
			Result.success(semesterEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getSemesterById(id: String): Result<Semester?> =
		try {
			val semester = semesterDao.getSemesterById(id)
			Result.success(semester?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createSemester(semester: Semester): Result<Unit> =
		try {
			val semesterEntity = semester.toEntity()
			semesterDao.insertSemester(semesterEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateSemester(semester: Semester): Result<Unit> =
		try {
			val semesterEntity = semester.toEntity()
			semesterDao.updateSemester(semesterEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteSemester(semesterId: String): Result<Unit> =
		try {
			val semester = semesterDao.getSemesterById(semesterId)
			semester?.let { semesterDao.deleteSemester(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncSemesters(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
