package com.moriawe.worktimer2.di

import android.app.Application
import androidx.room.Room
import com.moriawe.worktimer2.data.TimeDatabase
import com.moriawe.worktimer2.data.TimeRepositoryImpl
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
            .build()
    }

    @Provides
    @Singleton
    fun provideTimerRepository(db: TimeDatabase): TimeRepositoryImpl {
        return TimeRepositoryImpl(db.dao)
    }
}