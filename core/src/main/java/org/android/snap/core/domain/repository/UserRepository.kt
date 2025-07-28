package org.android.snap.core.domain.repository

import org.android.snap.core.domain.model.User

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface UserRepository {
	suspend fun getUserById(id: String): Result<User?>
	suspend fun createOrUpdateUser(user: User): Result<Unit>
	suspend fun deleteUser(id: String): Result<Unit>
	suspend fun syncUser(userId: String): Result<Unit> // Trừu tượng, không phụ thuộc Firebase
}
