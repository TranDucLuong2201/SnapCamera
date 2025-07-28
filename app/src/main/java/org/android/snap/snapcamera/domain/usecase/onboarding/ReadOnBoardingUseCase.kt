package org.android.snap.snapcamera.domain.usecase.onboarding

import kotlinx.coroutines.flow.Flow
import org.android.snap.snapcamera.data.repository.impl.OnBoardingRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class ReadOnBoardingUseCase(
	private val repository: OnBoardingRepository,
) {
	operator fun invoke(): Flow<Boolean> = repository.readOnBoardingState()
}
