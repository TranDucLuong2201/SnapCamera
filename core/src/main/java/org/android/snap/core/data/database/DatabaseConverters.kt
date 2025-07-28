package org.android.snap.core.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.android.snap.core.domain.model.NotePosition
import org.android.snap.core.domain.model.NoteStyle

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class DatabaseConverters {

	@TypeConverter
	fun fromStringList(value: List<String>): String {
		return Gson().toJson(value)
	}

	@TypeConverter
	fun toStringList(value: String): List<String> {
		return try {
			Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
		} catch (e: Exception) {
			e.printStackTrace()
			emptyList()
		}
	}

	@TypeConverter
	fun fromIntList(value: List<Int>): String {
		return Gson().toJson(value)
	}

	@TypeConverter
	fun toIntList(value: String): List<Int> {
		return try {
			Gson().fromJson(value, object : TypeToken<List<Int>>() {}.type)
		} catch (e: Exception) {
			e.printStackTrace()
			emptyList()
		}
	}

	@TypeConverter
	fun fromNotePosition(position: NotePosition?): String? {
		return position?.let { Gson().toJson(it) }
	}

	@TypeConverter
	fun toNotePosition(value: String?): NotePosition? {
		return value?.let {
			try {
				Gson().fromJson(it, NotePosition::class.java)
			} catch (e: Exception) {
				e.printStackTrace()
				null
			}
		}
	}

	@TypeConverter
	fun fromNoteStyle(style: NoteStyle?): String? {
		return style?.let { Gson().toJson(it) }
	}

	@TypeConverter
	fun toNoteStyle(value: String?): NoteStyle? {
		return value?.let {
			try {
				Gson().fromJson(it, NoteStyle::class.java)
			} catch (e: Exception) {
				e.printStackTrace()
				null
			}
		}
	}
}
