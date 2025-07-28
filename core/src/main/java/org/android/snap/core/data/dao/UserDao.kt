package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.android.snap.core.data.entities.UserEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface UserDao {
	@Query("SELECT * FROM users WHERE id = :id")
	suspend fun getUserById(id: String): UserEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertUser(user: UserEntity)

	@Update
	suspend fun updateUser(user: UserEntity)

	@Query("DELETE FROM users WHERE id = :id")
	suspend fun deleteUser(id: String)
}
