package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.TimeFormatters.yearMonthDayFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class GetTimeItemsForSpecificDateUseCase @Inject constructor(
    private val repo: TimeRepository
) {

    operator fun invoke(date: LocalDateTime): Flow<List<TimeItem>> {
        val timeItems = repo.getTimeItems()

        return timeItems.map { list ->
            list.filter { timeItem ->
                date
                    .format(yearMonthDayFormatter) == timeItem.startTime
                    .format(yearMonthDayFormatter)
            }
        }
    }

}
