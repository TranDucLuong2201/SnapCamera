package org.android.snap.snapcamera.data.dto

import org.android.snap.core.domain.model.NotePosition
import org.android.snap.core.domain.model.NoteStyle

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class ImageNoteDto(
	val id: String = "",
	val imageId: String = "",
	val content: String = "",
	val type: String = "",
	val position: NotePosition? = null,
	val style: NoteStyle? = null,
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
