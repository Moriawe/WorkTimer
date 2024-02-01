package com.moriawe.worktimer2.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem

@Database(
    entities = [TimeItem::class, CurrentStartTime::class],
    version = 1,
)
@TypeConverters(TimeConverter::class)

abstract class TimeDatabase: RoomDatabase() {

    abstract val dao: TimeDao

}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `CurrentStartTime` " +
                "(`id` INTEGER, " +
                "`currentStartTime` LOCALDATETIME, " +
                "`isTimerStarted` BOOLEAN)")
    }
}