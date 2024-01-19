package com.moriawe.worktimer2.domain.util

import java.time.format.DateTimeFormatter

object TimeFormatters {
    val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val yearMonthDayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM")
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMM")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
}

object TimeConstant {
    const val TIME_DEFAULT_STRING = "2001-01-01T01:01:01"
}