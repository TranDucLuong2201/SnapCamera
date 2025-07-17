package org.campusquest.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.campusquest.core.data.local.entities.ScheduleEntity

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules WHERE active = 1 AND semesterId = :semesterId ORDER BY dayOfWeek, startTime")
    suspend fun getActiveSchedules(semesterId: String): List<ScheduleEntity>

    @Query("SELECT * FROM schedules WHERE dayOfWeek = :dayOfWeek AND active = 1 AND semesterId = :semesterId ORDER BY startTime")
    suspend fun getSchedulesByDay(dayOfWeek: Int, semesterId: String): List<ScheduleEntity>

    @Query("SELECT * FROM schedules WHERE active = 1 AND semesterId = :semesterId AND startTime <= :currentTime AND endTime >= :currentTime AND dayOfWeek = :dayOfWeek")
    suspend fun getCurrentSchedule(currentTime: String, dayOfWeek: Int, semesterId: String): ScheduleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Update
    suspend fun updateSchedule(schedule: ScheduleEntity)

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)

    @Query("UPDATE schedules SET active = 0 WHERE id = :scheduleId")
    suspend fun deactivateSchedule(scheduleId: String)

    @Query("SELECT DISTINCT subject FROM schedules WHERE active = 1 AND semesterId = :semesterId")
    suspend fun getActiveSubjects(semesterId: String): List<String>

    @Query("SELECT * FROM schedules WHERE id = :id")
    suspend fun getScheduleById(id: String): ScheduleEntity?
}