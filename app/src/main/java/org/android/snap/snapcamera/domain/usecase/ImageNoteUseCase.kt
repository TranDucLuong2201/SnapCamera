package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.ImageNote
import org.android.snap.snapcamera.data.repository.ImageNoteRepositoryImpl
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class ImageNoteUseCase @Inject constructor(
	private val imageNoteRepository: ImageNoteRepositoryImpl,
) {

	fun getNotesByImage(imageId: String): Flow<Result<List<ImageNote>>> =
		imageNoteRepository.getNotesByImage(imageId)

	suspend fun getNoteById(id: String): Result<ImageNote?> =
		imageNoteRepository.getNoteById(id)

	suspend fun createNote(note: ImageNote): Result<Unit> =
		imageNoteRepository.createNote(note)

	suspend fun updateNote(note: ImageNote): Result<Unit> =
		imageNoteRepository.updateNote(note)

	suspend fun deleteNote(noteId: String): Result<Unit> =
		imageNoteRepository.deleteNote(noteId)

	suspend fun deleteNotesByImage(imageId: String): Result<Unit> =
		imageNoteRepository.deleteNotesByImage(imageId)

	suspend fun syncNotes(): Result<SyncResult> =
		imageNoteRepository.syncNotes()
}
