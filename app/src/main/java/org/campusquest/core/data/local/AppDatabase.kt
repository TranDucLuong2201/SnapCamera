package org.campusquest.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.campusquest.core.data.local.dao.ExamDao
import org.campusquest.core.data.local.dao.NotificationDao
import org.campusquest.core.data.local.dao.PhotoDao
import org.campusquest.core.data.local.dao.ScheduleDao
import org.campusquest.core.data.local.dao.SemesterDao
import org.campusquest.core.data.local.entities.ExamEntity
import org.campusquest.core.data.local.entities.NotificationEntity
import org.campusquest.core.data.local.entities.PhotoEntity
import org.campusquest.core.data.local.entities.ScheduleEntity
import org.campusquest.core.data.local.entities.SemesterEntity


/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Database(
    entities = [
        ScheduleEntity::class,
        PhotoEntity::class,
        ExamEntity::class,
        SemesterEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun photoDao(): PhotoDao
    abstract fun examDao(): ExamDao
    abstract fun semesterDao(): SemesterDao
    abstract fun notificationDao(): NotificationDao
}