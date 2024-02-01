package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.util.TimeFormatters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class GetTimeItemsForSpecificDate @Inject constructor(
    private val repo: TimeRepository
) {

    //TODO: Should return repository result?
    operator fun invoke(date: LocalDateTime): Flow<List<TimeItem>> {
        val timeItems = repo.getTimeItems()
        return timeItems.map { list ->
            list.filter { timeItem ->
                date
                    .format(TimeFormatters.yearMonthDayFormatter) == timeItem.startTime
                    .format(TimeFormatters.yearMonthDayFormatter)
            }
        }
    }
}
