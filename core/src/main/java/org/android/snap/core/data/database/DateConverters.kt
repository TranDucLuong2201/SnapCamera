@file:RequiresApi(Build.VERSION_CODES.O)

package org.android.snap.core.data.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateConverters {

	private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
	private val offsetFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
	private val zonedFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

	// --- LocalDate ---
	@TypeConverter
	@JvmStatic
	fun fromLocalDate(date: LocalDate?): Long? {
		return date?.toEpochDay()
	}

	@TypeConverter
	@JvmStatic
	fun fromLocalTime(time: LocalTime?): String? {
		return time?.format(DateTimeFormatter.ISO_LOCAL_TIME)
	}

	@TypeConverter
	@JvmStatic
	fun toLocalTime(value: String?): LocalTime? {
		return value?.let { LocalTime.parse(it) }
	}

	@TypeConverter
	@JvmStatic
	fun fromDayOfWeek(dayOfWeek: DayOfWeek?): Int? {
		return dayOfWeek?.value
	}

	@TypeConverter
	@JvmStatic
	fun toDayOfWeek(value: Int?): DayOfWeek? {
		return value?.let { DayOfWeek.of(it) }
	}

	@TypeConverter
	@JvmStatic
	fun toLocalDate(epochDay: Long?): LocalDate? {
		return epochDay?.let { LocalDate.ofEpochDay(it) }
	}

	// --- LocalDateTime ---
	@TypeConverter
	@JvmStatic
	fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
		return dateTime?.format(isoFormatter)
	}

	@TypeConverter
	@JvmStatic
	fun toLocalDateTime(value: String?): LocalDateTime? {
		return value?.let { LocalDateTime.parse(it, isoFormatter) }
	}

	// --- Instant ---
	@TypeConverter
	@JvmStatic
	fun fromInstant(instant: Instant?): Long? {
		return instant?.toEpochMilli()
	}

	@TypeConverter
	@JvmStatic
	fun toInstant(value: Long?): Instant? {
		return value?.let { Instant.ofEpochMilli(it) }
	}

	// --- OffsetDateTime ---
	@TypeConverter
	@JvmStatic
	fun fromOffsetDateTime(dateTime: OffsetDateTime?): String? {
		return dateTime?.format(offsetFormatter)
	}

	@TypeConverter
	@JvmStatic
	fun toOffsetDateTime(value: String?): OffsetDateTime? {
		return value?.let { OffsetDateTime.parse(it, offsetFormatter) }
	}

	// --- ZonedDateTime ---
	@TypeConverter
	@JvmStatic
	fun fromZonedDateTime(dateTime: ZonedDateTime?): String? {
		return dateTime?.format(zonedFormatter)
	}

	@TypeConverter
	@JvmStatic
	fun toZonedDateTime(value: String?): ZonedDateTime? {
		return value?.let { ZonedDateTime.parse(it, zonedFormatter) }
	}
}
