package org.campusquest.core.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.campusquest.android.ui.navigate.Destination
import org.campusquest.core.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {
    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.saveOnBoardingUseCase(completed = completed)
        }
    }

    fun onNavigateToSignIn(clearAndNavigate: (String) -> Unit) {
        clearAndNavigate(Destination.Authentication.route)
    }
}