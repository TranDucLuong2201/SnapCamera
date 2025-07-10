package org.campusquest.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.campusquest.core.domain.repository.DataStoreOperations
import org.campusquest.core.utils.Constant.PREFERENCES_KEY
import org.campusquest.core.utils.Constant.PREFERENCES_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(PREFERENCES_KEY)
    }

    private val dataStore = context.dataStore
    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preference ->
            preference[PreferencesKey.onBoardingKey] = completed
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