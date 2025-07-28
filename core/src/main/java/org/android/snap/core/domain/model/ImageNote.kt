package org.android.snap.core.domain.model

import org.android.snap.core.utils.NoteType
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class ImageNote(
	val id: String,
	val imageId: String,
	val content: String,
	val type: NoteType,
	val position: NotePosition?,
	val style: NoteStyle?,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
