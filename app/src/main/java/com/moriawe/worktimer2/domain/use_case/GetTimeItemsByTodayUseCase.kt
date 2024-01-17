package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.TimeDao
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTimeItemsByTodayUseCase @Inject constructor(
    private val repo: TimeRepository
) {

    val YearMonthDayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // TODO: How to sort this most effectivly? Flatmap?
    operator fun invoke(): Flow<List<TimeItem2>> {
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