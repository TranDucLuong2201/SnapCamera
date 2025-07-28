package org.android.snap.snapcamera.domain.usecase

import org.android.snap.snapcamera.domain.usecase.onboarding.ReadOnBoardingUseCase
import org.android.snap.snapcamera.domain.usecase.onboarding.SaveOnBoardingUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class OnBoardingUseCase(
	val readOnBoardingUseCase: ReadOnBoardingUseCase,
	val saveOnBoardingUseCase: SaveOnBoardingUseCase,
)
