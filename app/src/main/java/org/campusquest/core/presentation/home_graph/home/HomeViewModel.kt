package org.campusquest.core.presentation.home_graph.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.campusquest.core.domain.model.CurrentClass
import org.campusquest.core.domain.model.Schedule
import org.campusquest.core.domain.usecase.class_usecase.GetCurrentClassUseCase
import org.campusquest.core.domain.usecase.class_usecase.GetTodayScheduleUseCase
import org.campusquest.core.domain.usecase.manage_usecase.ManageScheduleUseCase
import org.campusquest.core.domain.usecase.savephot_usecase.PhotoGalleryUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentClassUseCase: GetCurrentClassUseCase,
    private val getTodayScheduleUseCase: GetTodayScheduleUseCase,
    private val photoGalleryUseCase: PhotoGalleryUseCase,
    private val manageScheduleUseCase: ManageScheduleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadDashboardData()
        startPeriodicUpdate()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            val currentClass = getCurrentClassUseCase()
            val todaySchedule = getTodayScheduleUseCase()
            val todayPhotos = photoGalleryUseCase.getTodayPhotos()

            _uiState.value = _uiState.value.copy(
                currentClass = currentClass,
                todaySchedule = todaySchedule,
                todayPhotosCount = todayPhotos.size,
                isLoading = false
            )
        }
    }

    private fun startPeriodicUpdate() {
        viewModelScope.launch {
            while (true) {
                delay(60000) // Update every minute
                loadDashboardData()
            }
        }
    }

    fun navigateToCamera() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToCamera)
        }
    }

    fun navigateToGallery() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToGallery)
        }
    }

    fun navigateToEndCourse() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToEndCourse)
        }
    }

    fun navigateToExamSchedule() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToExamSchedule)
        }
    }

    fun navigateToExport() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToExport)
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadDashboardData()
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val currentClass: CurrentClass? = null,
    val todaySchedule: List<Schedule> = emptyList(),
    val todayPhotosCount: Int = 0,
    val error: String? = null
)

sealed class HomeNavigationEvent {
    object NavigateToCamera : HomeNavigationEvent()
    object NavigateToGallery : HomeNavigationEvent()
    object NavigateToEndCourse : HomeNavigationEvent()
    object NavigateToExamSchedule : HomeNavigationEvent()
    object NavigateToExport : HomeNavigationEvent()
}