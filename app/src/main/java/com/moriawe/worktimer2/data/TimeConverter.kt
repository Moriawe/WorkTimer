package com.moriawe.worktimer2.data

import androidx.room.TypeConverter
import java.time.LocalDateTime

class TimeConverter {
    @TypeConverter
    fun fromTimeStampToDate(timeStamp: String): LocalDateTime? {
        return timeStamp?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun fromDateToString(date: LocalDateTime?): String? {
        return date?.toString()
    }

}