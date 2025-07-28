package org.android.snap.snapcamera.data.repository.impl

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.domain.repository.PreferenceRepository
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class OnBoardingRepository @Inject constructor(
	private val dataStore: PreferenceRepository,
) {
	suspend fun saveOnBoardingState(completed: Boolean) {
		dataStore.saveOnBoardingState(completed = completed)
	}

	fun readOnBoardingState(): Flow<Boolean> = dataStore.readOnBoardingState()
}
