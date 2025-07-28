package org.android.snap.core.data.mapper

import org.android.snap.core.data.entities.ImageNoteEntity
import org.android.snap.core.domain.model.ImageNote

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// ImageNote Mappers
fun ImageNoteEntity.toDomain(): ImageNote {
	return ImageNote(
		id = id,
		imageId = imageId,
		content = content,
		type = type,
		position = position,
		style = style,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

fun ImageNote.toEntity(): ImageNoteEntity {
	return ImageNoteEntity(
		id = id,
		imageId = imageId,
		content = content,
		type = type,
		position = position,
		style = style,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
