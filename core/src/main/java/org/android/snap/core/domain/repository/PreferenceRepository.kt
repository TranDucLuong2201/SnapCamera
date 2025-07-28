package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface PreferenceRepository {
	suspend fun getPreference(key: String): Result<String?>
	suspend fun setPreference(key: String, value: String): Result<Unit>
	suspend fun deletePreference(key: String): Result<Unit>
	suspend fun getBooleanPreference(key: String, defaultValue: Boolean = false): Result<Boolean>
	suspend fun setBooleanPreference(key: String, value: Boolean): Result<Unit>
	suspend fun getIntPreference(key: String, defaultValue: Int = 0): Result<Int>
	suspend fun setIntPreference(key: String, value: Int): Result<Unit>
	suspend fun saveOnBoardingState(completed: Boolean)
	fun readOnBoardingState(): Flow<Boolean>
}
