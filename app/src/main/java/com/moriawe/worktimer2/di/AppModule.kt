package com.moriawe.worktimer2.di

import android.app.Application
import androidx.room.Room
import com.moriawe.worktimer2.data.MIGRATION_1_2
import com.moriawe.worktimer2.data.TimeDatabase
import com.moriawe.worktimer2.data.TimeRepositoryImpl
import com.moriawe.worktimer2.domain.repository.TimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// DI in here lives as long as the app
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTimeDatabase(app: Application): TimeDatabase {
        return Room.databaseBuilder(
            app,
            TimeDatabase::class.java,
            "time_db"
        )
            //.addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideTimerRepository(db: TimeDatabase): TimeRepository {
        return TimeRepositoryImpl(db.dao)
    }
}