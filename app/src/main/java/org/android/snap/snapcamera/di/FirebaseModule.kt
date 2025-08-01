package org.android.snap.snapcamera.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
	@Provides
	@Singleton
	fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
		return CredentialManager.create(context)
	}

	@Provides
	@Singleton
	fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
}
