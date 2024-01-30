package com.moriawe.worktimer2.di

import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.DeleteTimeItemFromDatabase
import com.moriawe.worktimer2.domain.use_case.GetListOfMonth
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDate
import com.moriawe.worktimer2.domain.use_case.SaveTimeItemToDatabase
import com.moriawe.worktimer2.domain.use_case.UpdateTimeItemInDatabase
import com.moriawe.worktimer2.domain.use_case.validations.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.validations.ValidateStopTimeUseCase
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
    fun provideGetTimeItemsByTodayUseCase(repository: TimeRepository): GetTimeItemsForSpecificDate {
        return GetTimeItemsForSpecificDate(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTimeItemsSortedByDateUseCase(repository: TimeRepository): GetListOfMonth {
        return GetListOfMonth(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTimeItemFromDatabaseUseCase(repository: TimeRepository): DeleteTimeItemFromDatabase {
        return DeleteTimeItemFromDatabase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateTimeItemInDatabaseUseCase(repository: TimeRepository): UpdateTimeItemInDatabase {
        return UpdateTimeItemInDatabase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveTimeItemToDatabaseUseCase(repository: TimeRepository): SaveTimeItemToDatabase {
        return SaveTimeItemToDatabase(repository)
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