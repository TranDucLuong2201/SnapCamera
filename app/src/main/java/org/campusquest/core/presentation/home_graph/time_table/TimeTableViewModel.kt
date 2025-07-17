package org.campusquest.core.presentation.home_graph.time_table

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
class TimeTableViewModel @Inject constructor(
    private val manageScheduleUseCase: ManageScheduleUseCase,
    private val semesterManagementUseCase: SemesterManagementUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimeTableUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<TimeTableNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun addSchedule(subject: String, dayOfWeek: Int, startTime: String, endTime: String, room: String) {
        if (subject.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Tên môn học không được để trống")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            manageScheduleUseCase.addSchedule(subject, dayOfWeek, startTime, endTime, room).fold(
                onSuccess = { schedule ->
                    loadSchedules()
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Đã thêm môn học thành công"
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

    fun loadSchedules() {
        viewModelScope.launch {
            val schedules = manageScheduleUseCase.getActiveSchedules()
            _uiState.value = _uiState.value.copy(schedules = schedules)
        }
    }

    fun startSemester() {
        if (_uiState.value.schedules.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                error = "Vui lòng thêm ít nhất một môn học"
            )
            return
        }

        viewModelScope.launch {
            val semesterName = "Học kỳ ${System.currentTimeMillis()}"
            val startDate = System.currentTimeMillis()
            val endDate = startDate + (120 * 24 * 60 * 60 * 1000L) // 4 tháng

            semesterManagementUseCase.startNewSemester(semesterName, startDate, endDate).fold(
                onSuccess = {
                    _navigationEvent.emit(TimeTableNavigationEvent.NavigateToDashboard)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(error = error.message)
                }
            )
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null, error = null)
    }
}

data class TimeTableUiState(
    val isLoading: Boolean = false,
    val schedules: List<Schedule> = emptyList(),
    val error: String? = null,
    val message: String? = null
)

sealed class TimeTableNavigationEvent {
    object NavigateToDashboard : TimeTableNavigationEvent()
}