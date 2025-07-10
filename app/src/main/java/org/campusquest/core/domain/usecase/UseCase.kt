package org.campusquest.core.domain.usecase

import org.campusquest.core.domain.usecase.read_onboarding.ReadOnBoardingUseCase
import org.campusquest.core.domain.usecase.save_onboarding.SaveOnBoardingUseCase

data class UseCase(
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val saveOnBoardingUseCase: SaveOnBoardingUseCase
)
