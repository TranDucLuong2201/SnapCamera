package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.domain.model.Subject
import org.android.snap.snapcamera.data.repository.SubjectRepositoryImpl
import javax.inject.Inject

/**
 * Use case for Subject operations
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SubjectUseCase @Inject constructor(
	private val subjectRepository: SubjectRepositoryImpl,
) {

	/**
	 * Get subjects by semester
	 */
	fun getSubjectsBySemester(semesterId: String): Flow<Result<List<Subject>>> =
		subjectRepository.getSubjectsBySemester(semesterId)

	/**
	 * Get active subjects
	 */
	fun getActiveSubjects(): Flow<Result<List<Subject>>> =
		subjectRepository.getActiveSubjects()

	/**
	 * Get subject by ID
	 */
	suspend fun getSubjectById(id: String): Result<Subject?> =
		subjectRepository.getSubjectById(id)

	/**
	 * Create new subject
	 */
	suspend fun createSubject(subject: Subject): Result<Unit> =
		subjectRepository.createSubject(subject)

	/**
	 * Update subject
	 */
	suspend fun updateSubject(subject: Subject): Result<Unit> =
		subjectRepository.updateSubject(subject)

	/**
	 * Mark subject as completed
	 */
	suspend fun markSubjectAsCompleted(subjectId: String): Result<Unit> =
		subjectRepository.markSubjectAsCompleted(subjectId)

	/**
	 * Delete subject
	 */
	suspend fun deleteSubject(subjectId: String): Result<Unit> =
		subjectRepository.deleteSubject(subjectId)

	/**
	 * Sync subjects
	 */
	suspend fun syncSubjects(): Result<org.android.snap.core.common.SyncResult> =
		subjectRepository.syncSubjects()
}
