package org.android.snap.core.domain.model

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class NoteStyle(
	val color: String,
	val fontSize: Float,
	val fontWeight: String,
	val backgroundColor: String?,
)

data class NotePosition(
	val x: Float,
	val y: Float,
	val width: Float,
	val height: Float,
)
