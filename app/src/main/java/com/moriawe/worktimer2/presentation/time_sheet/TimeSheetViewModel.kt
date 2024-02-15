package com.moriawe.worktimer2.presentation.time_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.use_case.GetListOfMonth
import com.moriawe.worktimer2.domain.use_case.RepositoryResults
import com.moriawe.worktimer2.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    getListOfMonth: GetListOfMonth
) : ViewModel() {

    val TAG = "TIME SHEET VIEW MODEL"

    // -*- TIME SHEET STATES -*- //
    private val _overViewList = MutableStateFlow(emptyList<Month>())
    private val _timeSheetState = MutableStateFlow(TimeSheetState())

    // -*- UI EVENT FLOW -*- //
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            when (val result = getListOfMonth()) {
                is RepositoryResults.Success -> {
                    result.data?.let {
                        it.collect { months ->
                            _overViewList.value = months
                        }
                    }
                }
                is RepositoryResults.Error -> {
                    showSnackbar(R.string.network_error)
                }
            }

        }
    }

    // Expose the state to the UI
    val timeSheetState = combine(_timeSheetState, _overViewList) { state, months ->
        state.copy(
            months = months
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimeSheetState())

    private suspend fun showSnackbar(message: Int) {
        _eventFlow.emit(
            UiEvent.ShowSnackbar(
                message = message
            )
        )
    }
}