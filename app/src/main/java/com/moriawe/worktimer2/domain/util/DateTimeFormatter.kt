package com.moriawe.worktimer2.domain.util

import java.time.format.DateTimeFormatter

class DateTimeFormatter {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
}

object TimeConstant {
    const val TIME_DEFAULT_STRING = "2001-01-01T01:01:01"
    //const val localDateTime = LocalDateTime.parse(TIME_DEFAULT_STRING)
}