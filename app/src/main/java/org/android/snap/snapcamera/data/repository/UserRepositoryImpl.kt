package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import org.android.snap.core.data.dao.UserDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.User
import org.android.snap.core.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */
@RequiresApi(Build.VERSION_CODES.O)
class UserRepositoryImpl @Inject constructor(
	private val userDao: UserDao,
	private val firebaseAuth: FirebaseAuth,
) : UserRepository {
	override suspend fun getUserById(id: String): Result<User?> =
		try {
			val userEntity = userDao.getUserById(id)
			Result.success(userEntity?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createOrUpdateUser(user: User): Result<Unit> =
		try {
			userDao.insertUser(user.toEntity())
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteUser(id: String): Result<Unit> =
		try {
			userDao.deleteUser(id)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncUser(userId: String): Result<Unit> {
		TODO("Not yet implemented")
	}
}
