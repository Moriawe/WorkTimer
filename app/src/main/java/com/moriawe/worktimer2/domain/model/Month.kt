package com.moriawe.worktimer2.domain.model

data class Month(
    val name: String,
    val days: List<Day> = emptyList(),
    //val items: List<TimeCardItem>,
    var totalWorkTimeInHours: String = ""
)