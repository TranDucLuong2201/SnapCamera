package org.campusquest.core.data.local

import androidx.room.TypeConverter
import org.campusquest.core.data.local.entities.ExamType
import org.campusquest.core.data.local.entities.NotificationType

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class Converters {
    @TypeConverter
    fun fromExamType(value: ExamType): String = value.name

    @TypeConverter
    fun toExamType(value: String): ExamType = ExamType.valueOf(value)

    @TypeConverter
    fun fromNotificationType(value: NotificationType): String = value.name

    @TypeConverter
    fun toNotificationType(value: String): NotificationType = NotificationType.valueOf(value)
}