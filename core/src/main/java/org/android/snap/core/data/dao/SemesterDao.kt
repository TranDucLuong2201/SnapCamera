package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.data.database.relation.SemesterWithSubjects
import org.android.snap.core.data.entities.SemesterEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface SemesterDao {
	@Query("SELECT * FROM semesters ORDER BY startDate DESC")
	fun getAllSemesters(): Flow<List<SemesterEntity>>

	@Query("SELECT * FROM semesters WHERE isActive = 1")
	fun getActiveSemesters(): Flow<List<SemesterEntity>>

	@Query("SELECT * FROM semesters WHERE id = :id")
	suspend fun getSemesterById(id: String): SemesterEntity?

	@Transaction
	@Query("SELECT * FROM semesters WHERE id = :id")
	suspend fun getSemesterWithSubjects(id: String): SemesterWithSubjects?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSemester(semester: SemesterEntity)

	@Update
	suspend fun updateSemester(semester: SemesterEntity)

	@Delete
	suspend fun deleteSemester(semester: SemesterEntity)

	@Query("DELETE FROM semesters WHERE id = :id")
	suspend fun deleteSemesterById(id: String)
}
