package com.moriawe.worktimer2.di

import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.DeleteTimeItemFromDatabaseUseCase
import com.moriawe.worktimer2.domain.use_case.GetListOfMonthUseCase
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDateUseCase
import com.moriawe.worktimer2.domain.use_case.SaveTimeItemToDatabaseUseCase
import com.moriawe.worktimer2.domain.use_case.UpdateTimeItemInDatabaseUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStopTimeUseCase
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

    @Provides
    @ViewModelScoped
    fun provideDeleteTimeItemFromDatabaseUseCase(repository: TimeRepository): DeleteTimeItemFromDatabaseUseCase {
        return DeleteTimeItemFromDatabaseUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateTimeItemInDatabaseUseCase(repository: TimeRepository): UpdateTimeItemInDatabaseUseCase {
        return UpdateTimeItemInDatabaseUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveTimeItemToDatabaseUseCase(repository: TimeRepository): SaveTimeItemToDatabaseUseCase {
        return SaveTimeItemToDatabaseUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideValidateStartTimeUseCase(): ValidateStartTimeUseCase {
        return ValidateStartTimeUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideValidateStopTimeUseCase(): ValidateStopTimeUseCase {
        return ValidateStopTimeUseCase()
    }
}