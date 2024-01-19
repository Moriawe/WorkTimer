package com.moriawe.worktimer2.domain.model

data class Month(
    val name: String,
    val items: List<TimeCardItem>,
    var totalWorkTimeInHours: String = ""
)