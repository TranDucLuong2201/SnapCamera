package org.campusquest.core.domain.repository

import org.campusquest.core.domain.model.User

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun saveUser(user: User)
    suspend fun clearUser()
    suspend fun isUserLoggedIn(): Boolean
}