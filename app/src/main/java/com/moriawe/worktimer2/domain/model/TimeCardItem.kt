package com.moriawe.worktimer2.domain.model

data class TimeCardItem(
    val date: String,
    val month: String,
    val startTime: String,
    val endTime: String,
    val totalTime: String,
    val description: String
)