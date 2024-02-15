package com.moriawe.worktimer2.di

import android.content.Context
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.DeleteTimeItemFromDatabase
import com.moriawe.worktimer2.domain.use_case.GetCurrentStartTime
import com.moriawe.worktimer2.domain.use_case.GetListOfMonth
import com.moriawe.worktimer2.domain.use_case.GetTimeItemById
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDate
import com.moriawe.worktimer2.domain.use_case.SaveCurrentStartTime
import com.moriawe.worktimer2.domain.use_case.SaveTimeItemToDatabase
import com.moriawe.worktimer2.domain.use_case.UpdateTimeItemInDatabase
import com.moriawe.worktimer2.domain.use_case.validations.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.validations.ValidateStopTimeUseCase
import com.moriawe.worktimer2.domain.util.CsvExporter
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
    fun provideDeleteTimeItemFromDatabase(repository: TimeRepository): DeleteTimeItemFromDatabase {
        return DeleteTimeItemFromDatabase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCurrentStartTime(repository: TimeRepository): GetCurrentStartTime {
        return GetCurrentStartTime(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetListOfMonth(repository: TimeRepository): GetListOfMonth {
        return GetListOfMonth(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTimeItemById(repository: TimeRepository): GetTimeItemById {
        return GetTimeItemById(repository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetTimeItemsForSpecificDate(repository: TimeRepository): GetTimeItemsForSpecificDate {
        return GetTimeItemsForSpecificDate(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveCurrentStartTime(repository: TimeRepository): SaveCurrentStartTime {
        return SaveCurrentStartTime(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveTimeItemToDatabase(repository: TimeRepository): SaveTimeItemToDatabase {
        return SaveTimeItemToDatabase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateTimeItemInDatabase(repository: TimeRepository): UpdateTimeItemInDatabase {
        return UpdateTimeItemInDatabase(repository)
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

    @Provides
    @ViewModelScoped
    fun provideCsvExporter(context: Context, getListOfMonth: GetListOfMonth): CsvExporter {
        return CsvExporter(context, getListOfMonth)
    }
}