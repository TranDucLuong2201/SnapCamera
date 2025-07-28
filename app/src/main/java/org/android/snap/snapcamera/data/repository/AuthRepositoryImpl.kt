package org.android.snap.snapcamera.data.repository

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import org.android.snap.core.domain.model.User
import org.android.snap.core.domain.repository.AuthRepository
import org.android.snap.snapcamera.BuildConfig
import org.android.snap.snapcamera.data.mapper.toUser
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Singleton
@RequiresApi(Build.VERSION_CODES.O)
class AuthRepositoryImpl @Inject constructor(
	private val auth: FirebaseAuth,
	@ApplicationContext private val appContext: Context,
	private val credentialManager: CredentialManager,
) : AuthRepository {
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

	override suspend fun signInWithMicrosoft(activity: Activity): Result<User> = try {
		val pendingResult = auth.pendingAuthResult
		if (pendingResult != null) {
			val authResult = pendingResult.await()
			authResult.user?.toUser()?.let { Result.success(it) }
				?: Result.failure(Exception("No user found after Microsoft sign-in"))
		} else {
			val provider = OAuthProvider.newBuilder("microsoft.com")
				.build()
			val authResult = auth.startActivityForSignInWithProvider(activity, provider).await()
			authResult.user?.toUser()?.let { Result.success(it) }
				?: Result.failure(Exception("No user found after Microsoft sign-in"))
		}
	} catch (e: Exception) {
		Result.failure(e)
	}

	override suspend fun signOut(): Result<Unit> = runCatching {
		auth.signOut()
		credentialManager.clearCredentialState(ClearCredentialStateRequest())
	}

	override fun getCurrentUser(): Result<User> = runCatching {
		auth.currentUser?.toUser() ?: error("Chưa đăng nhập")
	}
}
