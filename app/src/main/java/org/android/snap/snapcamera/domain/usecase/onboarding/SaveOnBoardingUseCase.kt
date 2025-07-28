package org.android.snap.snapcamera.domain.usecase.onboarding

import org.android.snap.snapcamera.data.repository.impl.OnBoardingRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class SaveOnBoardingUseCase(
	private val repository: OnBoardingRepository,
) {
	suspend operator fun invoke(completed: Boolean) = repository.saveOnBoardingState(completed)
}
