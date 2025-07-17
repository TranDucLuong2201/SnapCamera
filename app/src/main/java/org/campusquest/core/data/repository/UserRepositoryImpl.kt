package org.campusquest.core.data.repository

import android.content.Context
import com.google.gson.Gson
import org.campusquest.core.domain.model.User
import org.campusquest.core.domain.repository.UserRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class UserRepositoryImpl(
    private val context: Context
) : UserRepository {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    override suspend fun getCurrentUser(): User? {
        val userJson = prefs.getString("current_user", null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else null
    }

    override suspend fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit().putString("current_user", userJson).apply()
    }

    override suspend fun clearUser() {
        prefs.edit().remove("current_user").apply()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return prefs.contains("current_user")
    }
}