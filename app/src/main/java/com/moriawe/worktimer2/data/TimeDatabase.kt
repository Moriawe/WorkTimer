package com.moriawe.worktimer2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moriawe.worktimer2.data.entity.TimeItem2
import dagger.Provides

@Database(
    entities = [TimeItem2::class],
    version = 1
)
@TypeConverters(TimeConverter::class)

abstract class TimeDatabase: RoomDatabase() {

    abstract val dao: TimeDao

//    companion object {
//
//        @Volatile
//        private var INSTANCE: TimeDatabase? = null
//
//        fun getInstance(context: Context): TimeDatabase {
//            synchronized(this) {
//                return INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    TimeDatabase::class.java,
//                    "CatShow_db"
//                )
//                    .build().also {
//                        INSTANCE = it
//                    }
//            }
//        }
//    }

}