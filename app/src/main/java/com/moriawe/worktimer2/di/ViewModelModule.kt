package com.moriawe.worktimer2.di

import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsByTodayUseCase
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsSortedByDateUseCase
import com.moriawe.worktimer2.presentation.timer.TimerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


// DI in here lives as long as the view-model lives
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    // USE CASE
    @Provides
    @ViewModelScoped
    fun provideGetTimeItemsByTodayUseCase(repository: TimeRepository): GetTimeItemsByTodayUseCase {
        return GetTimeItemsByTodayUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTimeItemsSortedByDateUseCase(repository: TimeRepository): GetTimeItemsSortedByDateUseCase {
        return GetTimeItemsSortedByDateUseCase(repository)
    }
}