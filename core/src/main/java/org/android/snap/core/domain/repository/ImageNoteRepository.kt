package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.ImageNote

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface ImageNoteRepository {
	fun getNotesByImage(imageId: String): Flow<Result<List<ImageNote>>>
	suspend fun getNoteById(id: String): Result<ImageNote?>
	suspend fun createNote(note: ImageNote): Result<Unit>
	suspend fun updateNote(note: ImageNote): Result<Unit>
	suspend fun deleteNote(noteId: String): Result<Unit>
	suspend fun deleteNotesByImage(imageId: String): Result<Unit>
	suspend fun syncNotes(): Result<SyncResult>
}
