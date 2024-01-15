package com.moriawe.worktimer2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moriawe.worktimer2.data.entity.TimeItem2

@Database(
    entities = [TimeItem2::class],
    version = 1
)
abstract class TimeDatabase: RoomDatabase() {

    abstract val dao: TimeDao

}