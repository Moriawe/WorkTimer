package com.moriawe.worktimer2.presentation.time_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.GetListOfMonthUseCase
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDateUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStopTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    private val repo: TimeRepository,
    private val validateStopTimeUseCase: ValidateStopTimeUseCase,
    private val validateStartTimeUseCase: ValidateStartTimeUseCase,
    private val getListOfMonthUseCase: GetListOfMonthUseCase,
    private val getTimeItemsForSpecificDateUseCase: GetTimeItemsForSpecificDateUseCase
) : ViewModel() {

    val TAG = "TIME SHEET VIEW MODEL"

    // -*- TIME SHEET STATES -*- //
    private val _overViewList = getListOfMonthUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _timeSheetState = MutableStateFlow(TimeSheetState())

    val timeSheetState = combine(_timeSheetState, _overViewList) { state, months ->
        state.copy(
            months = months
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimeSheetState())


}