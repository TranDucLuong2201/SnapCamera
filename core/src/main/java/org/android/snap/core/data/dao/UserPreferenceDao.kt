package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.android.snap.core.data.entities.UserPreferenceEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface UserPreferenceDao {
	@Query("SELECT * FROM user_preferences WHERE keys = :key")
	suspend fun getPreference(key: String): UserPreferenceEntity?

	@Query("SELECT value FROM user_preferences WHERE keys = :key")
	suspend fun getPreferenceValue(key: String): String?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun setPreference(preference: UserPreferenceEntity)

	@Delete
	suspend fun deletePreference(preference: UserPreferenceEntity)

	@Query("DELETE FROM user_preferences WHERE keys = :key")
	suspend fun deletePreferenceByKey(key: String)
}
