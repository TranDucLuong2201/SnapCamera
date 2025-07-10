package org.campusquest.core.domain.repository

import android.app.Activity
import org.campusquest.core.domain.model.User

interface AuthRepository {
    suspend fun signInWithGoogle(): Result<User>
    suspend fun signInWithMicrosoft(activity: Activity): Result<User>
    suspend fun signOut(): Result<Unit>
    fun getCurrentUser(): Result<User>
}
