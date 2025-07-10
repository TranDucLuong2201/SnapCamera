package org.campusquest.core.presentation.auth

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.campusquest.android.ui.navigate.Destination
import org.campusquest.core.domain.model.User
import org.campusquest.core.domain.repository.AuthRepository
import org.campusquest.core.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _hasUser = MutableStateFlow(false)
    val hasUser = _hasUser.asStateFlow()

    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        repository.getCurrentUser()
            .onSuccess {
                user = it
                _hasUser.value = true
            }
    }

    fun signInWithGoogle() = launchAuthAction { repository.signInWithGoogle() }

    fun signInWithMicrosoft(activity: Activity) =
        launchAuthAction { repository.signInWithMicrosoft(activity) }

    fun signOut() = launchAuthAction {
        repository.signOut().map { null }
    }

    private fun launchAuthAction(action: suspend () -> Result<User?>) = viewModelScope.launch {
        isLoading = true
        errorMessage = null

        action().onSuccess {
            user = it
            _hasUser.value = it != null
        }.onFailure {
            errorMessage = it.message
            _hasUser.value = false
        }

        isLoading = false
    }

    fun onClearAndNavigate(clearAndNavigate: (String) -> Unit) {
        clearAndNavigate(Destination.Home.route)
    }

    fun onSignOutClick(clearAndNavigate: (String) -> Unit) {
        clearAndNavigate(Destination.Splash.route)
    }
}
