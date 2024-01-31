package com.moriawe.worktimer2.domain.use_case

import androidx.annotation.StringRes
import com.moriawe.worktimer2.data.entity.TimeItem

sealed class RepositoryResults<T> {
    data class Success<T>(val data: T): RepositoryResults<T>()
    data class Error<T>(@StringRes val message: Int): RepositoryResults<T>()
}