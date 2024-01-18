package com.moriawe.worktimer2.presentation.time_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsSortedByDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    private val getTimeItemsSortedByDateUseCase: GetTimeItemsSortedByDateUseCase
) : ViewModel() {

    private val _overViewList = getTimeItemsSortedByDateUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TimeSheetState())

    val state = combine(_state, _overViewList) { state, months ->
        state.copy(
            months = months
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimeSheetState())

    //val timeItemList = generateTimeItemsList(25)



}
