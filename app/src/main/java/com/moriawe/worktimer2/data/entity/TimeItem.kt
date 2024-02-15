package com.moriawe.worktimer2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moriawe.worktimer2.data.entity.TimeItem.Companion.TABLE_NAME
import java.time.LocalDateTime

@Entity //(tableName = TABLE_NAME)
data class TimeItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = START_TIME) val startTime: LocalDateTime,
    @ColumnInfo(name = STOP_TIME)val stopTime: LocalDateTime,
    @ColumnInfo(name = DESCRIPTION)var description: String = ""
) {
    companion object {
        const val TABLE_NAME = "Time Sheet"
        const val START_TIME = "Start time"
        const val STOP_TIME = "Stop time"
        const val DESCRIPTION = "Work description"
    }
}
