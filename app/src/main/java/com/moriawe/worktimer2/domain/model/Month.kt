package com.moriawe.worktimer2.domain.model

data class Month(
    val name: String,
    val days: List<Day> = emptyList(),
    var totalWorkTimeInHours: String = ""
)