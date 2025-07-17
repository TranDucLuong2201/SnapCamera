package org.campusquest.core.presentation.home_graph.gallery

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.usecase.manage_usecase.ManageScheduleUseCase
import org.campusquest.core.domain.usecase.savephot_usecase.PhotoGalleryUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@HiltViewModel
class PhotoGalleryViewModel @Inject constructor(
    private val photoGalleryUseCase: PhotoGalleryUseCase,
    private val manageScheduleUseCase: ManageScheduleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoGalleryUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<PhotoGalleryNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadTodayPhotos()
    }

    fun loadTodayPhotos() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val photos = photoGalleryUseCase.getTodayPhotos()
            _uiState.value = _uiState.value.copy(
                photos = photos,
                isLoading = false,
                currentFilter = GalleryFilter.TODAY
            )
        }
    }

    fun loadPhotosBySubject(subject: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val photos = photoGalleryUseCase.getPhotosBySubject(subject)
            _uiState.value = _uiState.value.copy(
                photos = photos,
                isLoading = false,
                currentFilter = GalleryFilter.SUBJECT,
                selectedSubject = subject
            )
        }
    }

    fun loadPhotosByDay(day: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val photos = photoGalleryUseCase.getPhotosByDay(day)
            _uiState.value = _uiState.value.copy(
                photos = photos,
                isLoading = false,
                currentFilter = GalleryFilter.DAY,
                selectedDay = day
            )
        }
    }

    fun togglePhotoSelection(photo: Photo) {
        val currentSelected = _uiState.value.selectedPhotos.toMutableList()
        if (currentSelected.contains(photo)) {
            currentSelected.remove(photo)
        } else {
            currentSelected.add(photo)
        }
        _uiState.value = _uiState.value.copy(selectedPhotos = currentSelected)
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedPhotos = emptyList())
    }

    fun deleteSelectedPhotos() {
        val selectedPhotos = _uiState.value.selectedPhotos
        if (selectedPhotos.isEmpty()) return

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val photoIds = selectedPhotos.map { it.id }
            photoGalleryUseCase.deletePhotos(photoIds).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        selectedPhotos = emptyList(),
                        message = "Đã xóa ${selectedPhotos.size} ảnh"
                    )
                    // Reload current view
                    when (_uiState.value.currentFilter) {
                        GalleryFilter.TODAY -> loadTodayPhotos()
                        GalleryFilter.SUBJECT -> loadPhotosBySubject(_uiState.value.selectedSubject ?: "")
                        GalleryFilter.DAY -> loadPhotosByDay(_uiState.value.selectedDay ?: "")
                    }
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

    fun shareSelectedPhotos(context: Context) {
        val selectedPhotos = _uiState.value.selectedPhotos
        if (selectedPhotos.isEmpty()) return

        viewModelScope.launch {
            photoGalleryUseCase.sharePhotos(selectedPhotos, context).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        message = "Đã tạo file chia sẻ thành công"
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message
                    )
                }
            )
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null, error = null)
    }
}

data class PhotoGalleryUiState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val selectedPhotos: List<Photo> = emptyList(),
    val currentFilter: GalleryFilter = GalleryFilter.TODAY,
    val selectedSubject: String? = null,
    val selectedDay: String? = null,
    val error: String? = null,
    val message: String? = null
)

enum class GalleryFilter {
    TODAY, SUBJECT, DAY
}

sealed class PhotoGalleryNavigationEvent {
    object NavigateBack : PhotoGalleryNavigationEvent()
}