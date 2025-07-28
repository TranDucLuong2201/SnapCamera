package org.android.snap.snapcamera.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.android.snap.core.domain.repository.PreferenceRepository
import org.android.snap.snapcamera.utils.Constant.PREFERENCES_KEY
import org.android.snap.snapcamera.utils.Constant.PREFERENCES_NAME

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class PreferenceRepositoryImpl(
	context: Context,
) : PreferenceRepository {

	private val dataStore = context.dataStore
	override suspend fun getPreference(key: String): Result<String?> = runCatching {
		val preferencesKey = stringPreferencesKey(key)
		dataStore.data.first()[preferencesKey]
	}

	override suspend fun setPreference(
		key: String,
		value: String,
	): Result<Unit> = runCatching {
		val preferenceKey = stringPreferencesKey(key)
		dataStore.edit { it[preferenceKey] = value }
	}

	override suspend fun deletePreference(key: String): Result<Unit> = runCatching {
		val stringKey = stringPreferencesKey(key)
		val booleanKey = booleanPreferencesKey(key)
		val intKey = intPreferencesKey(key)
		val doubleKey = doublePreferencesKey(key)
		val floatKey = floatPreferencesKey(key)
		val longKey = longPreferencesKey(key)

		dataStore.edit {
			it.remove(stringKey)
			it.remove(booleanKey)
			it.remove(intKey)
			it.remove(doubleKey)
			it.remove(floatKey)
			it.remove(longKey)
		}
	}

	override suspend fun getBooleanPreference(
		key: String,
		defaultValue: Boolean,
	): Result<Boolean> = runCatching {
		val preferenceKey = booleanPreferencesKey(key)
		dataStore.data.first()[preferenceKey] ?: defaultValue
	}

	override suspend fun setBooleanPreference(
		key: String,
		value: Boolean,
	): Result<Unit> = runCatching {
		val preferenceKey = booleanPreferencesKey(key)
		dataStore.edit { it[preferenceKey] = value }
	}

	override suspend fun getIntPreference(
		key: String,
		defaultValue: Int,
	): Result<Int> = runCatching {
		val preferenceKey = intPreferencesKey(key)
		dataStore.data.first()[preferenceKey] ?: defaultValue
	}

	override suspend fun setIntPreference(
		key: String,
		value: Int,
	): Result<Unit> = runCatching {
		val preferenceKey = intPreferencesKey(key)
		dataStore.edit { it[preferenceKey] = value }
	}

	// --- Generic Handlers ---
	private object PreferencesKey {
		val onBoardingKey = booleanPreferencesKey(PREFERENCES_KEY)
	}

	override suspend fun saveOnBoardingState(completed: Boolean) {
		dataStore.edit { preferences ->
			preferences[PreferencesKey.onBoardingKey] = completed
		}
	}

	override fun readOnBoardingState(): Flow<Boolean> = dataStore.data
		.catch { exception ->
			if (exception is IOException) {
				emit(emptyPreferences())
			} else {
				throw exception
			}
		}
		.map { preference ->
			val onBoardingState = preference[PreferencesKey.onBoardingKey] ?: false
			onBoardingState
		}
}
