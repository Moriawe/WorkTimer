package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.entity.TimeItem
import java.time.Duration

class CalculateTotalTimeUseCase() {
    operator fun invoke(timeItems: List<TimeItem>): Duration {

        var totalTime: Duration = Duration.ZERO

        timeItems.forEach { item ->
            totalTime += Duration.between(item.startTime, item.stopTime)
        }
        return totalTime
    }

}