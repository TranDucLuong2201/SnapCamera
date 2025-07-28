package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.android.snap.core.domain.model.NotePosition
import org.android.snap.core.domain.model.NoteStyle
import org.android.snap.core.utils.DbConstants.ImageNote.FK_CHILD
import org.android.snap.core.utils.DbConstants.ImageNote.FK_PARENT
import org.android.snap.core.utils.DbConstants.ImageNote.TABLE_NAME
import org.android.snap.core.utils.NoteType
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(
	tableName = TABLE_NAME,
	foreignKeys = [
		ForeignKey(
			entity = LectureImageEntity::class,
			parentColumns = [FK_PARENT],
			childColumns = [FK_CHILD],
			onDelete = ForeignKey.CASCADE,
		),
	],
	indices = [
		Index("imageId"),
		Index("type"),
		Index("createdAt"),
		Index("updatedAt"),
	],
)
data class ImageNoteEntity(
	@PrimaryKey val id: String,
	val imageId: String,
	// Text note content
	val content: String,
	// "text", "handwritten", "annotation"
	val type: NoteType,
	// JSON cho vị trí ghi chú (x, y, width, height)
	val position: NotePosition?,
	// JSON cho style (color, size, font...)
	val style: NoteStyle?,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
