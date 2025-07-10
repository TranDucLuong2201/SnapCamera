package org.campusquest.core.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.campusquest.android.ui.navigate.Destination
import org.campusquest.core.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingCompleted.value =
                useCase.readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }

    fun onGetStartedClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Destination.Authentication.route, Destination.Splash.route)
    }
    fun onDefault(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Destination.OnBoarding.route, Destination.Splash.route)
    }
}