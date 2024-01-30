package com.moriawe.worktimer2.domain.use_case

import androidx.annotation.StringRes
import com.moriawe.worktimer2.data.entity.TimeItem

sealed class RepositoryResults {
    data class Success(val timeItem: TimeItem): RepositoryResults()
    //data object Success(): RepositoryResults()
    data class Error(@StringRes val message: Int): RepositoryResults()
    //data object Loading: RepositoryResults()
}