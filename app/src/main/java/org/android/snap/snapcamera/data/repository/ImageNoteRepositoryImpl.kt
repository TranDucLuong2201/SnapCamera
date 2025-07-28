package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.dao.ImageNoteDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.ImageNote
import org.android.snap.core.domain.repository.ImageNoteRepository
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class ImageNoteRepositoryImpl @Inject constructor(
	private val imageNoteDao: ImageNoteDao,
) : ImageNoteRepository {

	override fun getNotesByImage(imageId: String): Flow<Result<List<ImageNote>>> =
		imageNoteDao.getNotesByImage(imageId).map { noteEntities ->
			Result.success(noteEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getNoteById(id: String): Result<ImageNote?> =
		try {
			val note = imageNoteDao.getNoteById(id)
			Result.success(note?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createNote(note: ImageNote): Result<Unit> =
		try {
			val noteEntity = note.toEntity()
			imageNoteDao.insertNote(noteEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateNote(note: ImageNote): Result<Unit> =
		try {
			val noteEntity = note.toEntity()
			imageNoteDao.updateNote(noteEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteNote(noteId: String): Result<Unit> =
		try {
			val note = imageNoteDao.getNoteById(noteId)
			note?.let { imageNoteDao.deleteNote(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteNotesByImage(imageId: String): Result<Unit> =
		try {
			imageNoteDao.deleteNotesByImage(imageId)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncNotes(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
