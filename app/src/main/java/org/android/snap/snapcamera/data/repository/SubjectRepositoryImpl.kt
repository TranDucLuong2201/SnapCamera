package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.dao.SubjectDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.Subject
import org.android.snap.core.domain.repository.SubjectRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SubjectRepositoryImpl @Inject constructor(
	private val subjectDao: SubjectDao,
) : SubjectRepository {

	override fun getSubjectsBySemester(semesterId: String): Flow<Result<List<Subject>>> =
		subjectDao.getSubjectsBySemester(semesterId).map { subjectEntities ->
			Result.success(subjectEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override fun getActiveSubjects(): Flow<Result<List<Subject>>> =
		subjectDao.getActiveSubjects().map { subjectEntities ->
			Result.success(subjectEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getSubjectById(id: String): Result<Subject?> =
		try {
			val subject = subjectDao.getSubjectById(id)
			Result.success(subject?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createSubject(subject: Subject): Result<Unit> =
		try {
			val subjectEntity = subject.toEntity()
			subjectDao.insertSubject(subjectEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateSubject(subject: Subject): Result<Unit> =
		try {
			val subjectEntity = subject.toEntity()
			subjectDao.updateSubject(subjectEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun markSubjectAsCompleted(subjectId: String): Result<Unit> =
		try {
			subjectDao.markAsCompleted(subjectId, LocalDateTime.now())
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteSubject(subjectId: String): Result<Unit> =
		try {
			val subject = subjectDao.getSubjectById(subjectId)
			subject?.let { subjectDao.deleteSubject(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncSubjects(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
