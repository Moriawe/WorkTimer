package com.moriawe.worktimer2.presentation.time_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.domain.use_case.GetListOfMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    getListOfMonth: GetListOfMonth,
) : ViewModel() {

    val TAG = "TIME SHEET VIEW MODEL"

    // -*- TIME SHEET STATES -*- //
    private val _overViewList = getListOfMonth()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _timeSheetState = MutableStateFlow(TimeSheetState())

    val timeSheetState = combine(_timeSheetState, _overViewList) { state, months ->
        state.copy(
            months = months
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimeSheetState())


}