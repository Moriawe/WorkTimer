package com.moriawe.worktimer2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moriawe.worktimer2.data.entity.TimeItem

@Database(
    entities = [TimeItem::class],
    version = 1
)
@TypeConverters(TimeConverter::class)

abstract class TimeDatabase: RoomDatabase() {

    abstract val dao: TimeDao

}