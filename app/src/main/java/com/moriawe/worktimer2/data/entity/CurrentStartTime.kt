package com.moriawe.worktimer2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class CurrentStartTime(
    @PrimaryKey
    val id: Int = 1,
    val currentStartTime: LocalDateTime? = null,
    val isTimerStarted: Boolean = false
)