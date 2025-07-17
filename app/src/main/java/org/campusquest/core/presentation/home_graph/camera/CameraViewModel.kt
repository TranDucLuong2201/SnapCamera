package org.campusquest.core.presentation.home_graph.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.campusquest.core.domain.model.CurrentClass
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.usecase.class_usecase.GetCurrentClassUseCase
import org.campusquest.core.domain.usecase.savephot_usecase.SavePhotoUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */


@HiltViewModel
class CameraViewModel @Inject constructor(
    private val savePhotoUseCase: SavePhotoUseCase,
    private val getCurrentClassUseCase: GetCurrentClassUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CameraNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadCurrentClass()
    }

    private fun loadCurrentClass() {
        viewModelScope.launch {
            val currentClass = getCurrentClassUseCase()
            _uiState.value = _uiState.value.copy(currentClass = currentClass)
        }
    }

    fun capturePhoto(uri: Uri, note: String = "") {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            savePhotoUseCase(uri, note).fold(
                onSuccess = { photo ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Đã lưu ảnh thành công",
                        lastSavedPhoto = photo
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.emit(CameraNavigationEvent.NavigateBack)
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null, error = null)
    }
}

data class CameraUiState(
    val isLoading: Boolean = false,
    val currentClass: CurrentClass? = null,
    val lastSavedPhoto: Photo? = null,
    val error: String? = null,
    val message: String? = null
)

sealed class CameraNavigationEvent {
    object NavigateBack : CameraNavigationEvent()
}