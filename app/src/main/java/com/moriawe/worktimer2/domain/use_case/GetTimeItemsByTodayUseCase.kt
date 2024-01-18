package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTimeItemsByTodayUseCase @Inject constructor(
    private val repo: TimeRepository
) {

    // TODO: Use other class
    val YearMonthDayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    operator fun invoke(): Flow<List<TimeItem>> {
        val timeItems = repo.getTimeItems()

        timeItems.map { list ->
            list.filter { timeItem ->
                LocalDateTime.now()
                    .format(YearMonthDayFormatter) == timeItem.startTime
                        .format(YearMonthDayFormatter)
            }
        }
        return timeItems
    }

}
