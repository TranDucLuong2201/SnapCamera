package org.campusquest.core.domain.usecase.read_onboarding

import kotlinx.coroutines.flow.Flow
import org.campusquest.core.data.repository.Repository

class ReadOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}