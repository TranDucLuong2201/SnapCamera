package org.android.snap.core.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.android.snap.core.data.entities.ImageNoteEntity
import org.android.snap.core.data.entities.LectureImageEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class LectureImageWithNotes(
	@Embedded val image: LectureImageEntity,
	@Relation(
		parentColumn = "id",
		entityColumn = "imageId",
	)
	val notes: List<ImageNoteEntity>,
)
