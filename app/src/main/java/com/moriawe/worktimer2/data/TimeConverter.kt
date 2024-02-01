package com.moriawe.worktimer2.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class TimeConverter {
    @TypeConverter
    fun fromTimeStampToDate(timeStamp: String?): LocalDateTime? {
        return timeStamp?.let {
            try {
                LocalDateTime.parse(it)
            } catch (e: DateTimeParseException) {
                null
            }
        }
    }

    @TypeConverter
    fun fromDateToString(date: LocalDateTime?): String? {
        return date?.toString()
    }

}