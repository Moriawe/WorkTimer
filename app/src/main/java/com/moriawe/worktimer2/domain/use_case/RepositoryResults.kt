package com.moriawe.worktimer2.domain.use_case

import androidx.annotation.StringRes

sealed class RepositoryResults<T> {
    data class Success<T>(val data: T? = null): RepositoryResults<T>()
    data class Error<T>(@StringRes val message: Int): RepositoryResults<T>()
}

/*
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

 */