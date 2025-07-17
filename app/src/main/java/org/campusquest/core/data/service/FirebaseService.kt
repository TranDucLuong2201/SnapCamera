package org.campusquest.core.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import org.campusquest.core.domain.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class FirebaseService {
    private val auth = FirebaseAuth.getInstance()
    private val messaging = FirebaseMessaging.getInstance()

    suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                val user = User(
                    id = firebaseUser.uid,
                    displayName = firebaseUser.displayName ?: "",
                    photoUrl = firebaseUser.photoUrl?.toString(),
                    email = firebaseUser.email ?: "",
                    joinDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                )
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUserWithEmail(email: String, password: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                val user = User(
                    id = firebaseUser.uid,
                    displayName = firebaseUser.displayName ?: "",
                    photoUrl = firebaseUser.photoUrl?.toString(),
                    email = firebaseUser.email ?: "",
                    joinDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                )
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to create user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return if (firebaseUser != null) {
            User(
                id = firebaseUser.uid,
                displayName = firebaseUser.displayName ?: "",
                photoUrl = firebaseUser.photoUrl?.toString(),
                email = firebaseUser.email ?: "",
                joinDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            )
        } else null
    }

    suspend fun getFCMToken(): String? {
        return try {
            messaging.token.await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun subscribeToTopic(topic: String) {
        try {
            messaging.subscribeToTopic(topic).await()
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun unsubscribeFromTopic(topic: String) {
        try {
            messaging.unsubscribeFromTopic(topic).await()
        } catch (e: Exception) {
            // Handle error
        }
    }
}