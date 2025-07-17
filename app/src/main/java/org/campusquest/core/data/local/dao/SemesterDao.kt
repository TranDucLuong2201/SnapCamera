package org.campusquest.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.campusquest.core.data.local.entities.SemesterEntity

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface SemesterDao {
    @Query("SELECT * FROM semesters ORDER BY createdAt DESC")
    suspend fun getAllSemesters(): List<SemesterEntity>

    @Query("SELECT * FROM semesters WHERE active = 1 LIMIT 1")
    suspend fun getActiveSemester(): SemesterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSemester(semester: SemesterEntity)

    @Update
    suspend fun updateSemester(semester: SemesterEntity)

    @Query("UPDATE semesters SET active = 0")
    suspend fun deactivateAllSemesters()

    @Query("UPDATE semesters SET active = 1 WHERE id = :semesterId")
    suspend fun activateSemester(semesterId: String)

    @Query("SELECT * FROM semesters WHERE id = :id")
    suspend fun getSemesterById(id: String): SemesterEntity?
}