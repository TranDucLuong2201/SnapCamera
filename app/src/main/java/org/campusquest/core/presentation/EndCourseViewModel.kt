package org.campusquest.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.campusquest.core.domain.model.Schedule
import org.campusquest.core.domain.usecase.manage_usecase.ManageScheduleUseCase
import org.campusquest.core.domain.usecase.manage_usecase.SemesterManagementUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@HiltViewModel
class EndCourseViewModel @Inject constructor(
    private val manageScheduleUseCase: ManageScheduleUseCase,
    private val semesterManagementUseCase: SemesterManagementUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EndCourseUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<EndCourseNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadActiveSchedules()
    }

    private fun loadActiveSchedules() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val schedules = manageScheduleUseCase.getActiveSchedules()
            _uiState.value = _uiState.value.copy(
                activeSchedules = schedules,
                isLoading = false
            )
        }
    }

    fun endCourse(schedule: Schedule, reason: String = "") {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            manageScheduleUseCase.endCourse(schedule.id).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        message = "Đã kết thúc môn ${schedule.subject}"
                    )
                    loadActiveSchedules()

                    // Check if ready for new semester
                    if (semesterManagementUseCase.isReadyForNewSemester()) {
                        _navigationEvent.emit(EndCourseNavigationEvent.ShowNewSemesterDialog)
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

    fun startNewSemester() {
        viewModelScope.launch {
            semesterManagementUseCase.endCurrentSemester()
            _navigationEvent.emit(EndCourseNavigationEvent.NavigateToTimeTable)
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null, error = null)
    }
}

data class EndCourseUiState(
    val isLoading: Boolean = false,
    val activeSchedules: List<Schedule> = emptyList(),
    val error: String? = null,
    val message: String? = null
)

sealed class EndCourseNavigationEvent {
    object ShowNewSemesterDialog : EndCourseNavigationEvent()
    object NavigateToTimeTable : EndCourseNavigationEvent()
}