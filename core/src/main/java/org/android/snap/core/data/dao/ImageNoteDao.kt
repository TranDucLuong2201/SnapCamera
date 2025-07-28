package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.entities.ImageNoteEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface ImageNoteDao {
	@Query("SELECT * FROM image_notes WHERE imageId = :imageId ORDER BY createdAt ASC")
	fun getNotesByImage(imageId: String): Flow<List<ImageNoteEntity>>

	@Query("SELECT * FROM image_notes WHERE id = :id")
	suspend fun getNoteById(id: String): ImageNoteEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertNote(note: ImageNoteEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertNotes(notes: List<ImageNoteEntity>)

	@Update
	suspend fun updateNote(note: ImageNoteEntity)

	@Delete
	suspend fun deleteNote(note: ImageNoteEntity)

	@Query("DELETE FROM image_notes WHERE imageId = :imageId")
	suspend fun deleteNotesByImage(imageId: String)
}
