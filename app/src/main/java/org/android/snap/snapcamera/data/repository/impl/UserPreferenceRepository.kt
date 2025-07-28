package org.android.snap.snapcamera.data.repository.impl

import org.android.snap.core.domain.repository.PreferenceRepository
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class UserPreferenceRepository @Inject constructor(
	private val dataStore: PreferenceRepository,
) {
	suspend fun setPreference(key: String, value: String): Result<Unit> = dataStore.setPreference(key, value)

	suspend fun getPreference(key: String): Result<String?> = dataStore.getPreference(key)

	suspend fun deletePreference(key: String): Result<Unit> = dataStore.deletePreference(key)

	suspend fun getBooleanPreference(key: String, defaultValue: Boolean = false): Result<Boolean> = dataStore.getBooleanPreference(key, defaultValue)

	suspend fun setBooleanPreference(key: String, value: Boolean): Result<Unit> = dataStore.setBooleanPreference(key, value)

	suspend fun getIntPreference(key: String, defaultValue: Int = 0): Result<Int> = dataStore.getIntPreference(key, defaultValue)

	suspend fun setIntPreference(key: String, value: Int): Result<Unit> = dataStore.setIntPreference(key, value)
}
