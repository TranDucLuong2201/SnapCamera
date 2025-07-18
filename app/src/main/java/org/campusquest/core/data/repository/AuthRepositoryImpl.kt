package org.campusquest.core.data.repository

import android.app.Activity
import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import org.campusquest.android.BuildConfig
import org.campusquest.core.data.mapper.toUser
import org.campusquest.core.domain.model.User
import org.campusquest.core.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @ApplicationContext private val appContext: Context,
    private val credentialManager: CredentialManager
) : AuthRepository {

    override fun getCurrentUser(): Result<User> = runCatching {
        auth.currentUser?.toUser() ?: error("Chưa đăng nhập")
    }

    override suspend fun signInWithGoogle(): Result<User> = runCatching {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(BuildConfig.API_KEY)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(appContext, request)
        val credential = result.credential

        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val user = auth.signInWithCredential(firebaseCredential).await().user
            user?.toUser() ?: error("Không lấy được thông tin người dùng từ Firebase")
        } else {
            error("Credential không hợp lệ")
        }
    }

    override suspend fun signInWithMicrosoft(activity: Activity): Result<User> = runCatching {
        val provider = OAuthProvider.newBuilder("microsoft.com")
            .setScopes(listOf("email", "profile", "openid", "User.Read"))
            .addCustomParameter("prompt", "consent")

        val authResult = auth.pendingAuthResult?.await()
            ?: auth.startActivityForSignInWithProvider(activity, provider.build()).await()

        authResult.user?.toUser() ?: error("Microsoft Sign-In không trả lại thông tin người dùng")
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        auth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}
