package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.Subject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface SubjectRepository {
	fun getSubjectsBySemester(semesterId: String): Flow<Result<List<Subject>>>
	fun getActiveSubjects(): Flow<Result<List<Subject>>>
	suspend fun getSubjectById(id: String): Result<Subject?>
	suspend fun createSubject(subject: Subject): Result<Unit>
	suspend fun updateSubject(subject: Subject): Result<Unit>
	suspend fun markSubjectAsCompleted(subjectId: String): Result<Unit>
	suspend fun deleteSubject(subjectId: String): Result<Unit>
	suspend fun syncSubjects(): Result<SyncResult>
}
