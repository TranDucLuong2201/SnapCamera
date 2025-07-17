package org.campusquest.core.presentation.home_graph.me

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.campusquest.core.domain.model.User
import org.campusquest.core.domain.repository.AuthRepository
import org.campusquest.core.presentation.ui.navigate.Destination
import javax.inject.Inject

/**
 * Created by Duclu on 7/17/2025.
 * Email: ducluongtran597@gmail.com
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        _userState.value = UserState(isLoading = true)
        authRepository.getCurrentUser().fold(
            onSuccess = { user ->
                _userState.value = UserState(user = user)
            },
            onFailure = { error ->
                _userState.value = UserState(error = error.message)
            }
        )
    }

    suspend fun signOut(context: Context) {
        authRepository.signOut().fold(
            onSuccess = {
                // Navigate to login screen or handle post-sign-out
                // This depends on your app's navigation flow
            },
            onFailure = { error ->
                _userState.value = UserState(error = error.message)
            }
        )
    }

    fun openScreen(navigate: (String) -> Unit, destination: String) {
        try {
            navigate(destination)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class UserState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)