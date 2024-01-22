package com.moriawe.worktimer2.domain.model

data class Day(
    val date: String,
    val items: List<TimeCardItem>,
    var totalWorkTime: String = ""
)

