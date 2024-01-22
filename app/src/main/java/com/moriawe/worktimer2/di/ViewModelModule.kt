package com.moriawe.worktimer2.di

import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDateUseCase
import com.moriawe.worktimer2.domain.use_case.GetListOfMonthUseCase
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
    fun provideGetTimeItemsByTodayUseCase(repository: TimeRepository): GetTimeItemsForSpecificDateUseCase {
        return GetTimeItemsForSpecificDateUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTimeItemsSortedByDateUseCase(repository: TimeRepository): GetListOfMonthUseCase {
        return GetListOfMonthUseCase(repository)
    }
}