package org.campusquest.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.campusquest.core.domain.repository.DataStoreOperations
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreOperations
) {
    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }
}