package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.mapper.mapTimeItemToTimeCardItem
import com.moriawe.worktimer2.domain.model.Day
import com.moriawe.worktimer2.domain.model.Month


fun convertTimeItemListToMonthList(timeItemList: List<TimeItem>): List<Month> {
    val monthList = timeItemList
        .sortedBy { it.startTime }
        .map { mapTimeItemToTimeCardItem(it) }
        .groupBy { it.month }
        .map { month ->
            val sortedDays = month.value
                .groupBy { it.date }
                .map { (date, items) ->
                    val totalDayTime = calculateTotalTime(items)
                    Day(
                        date = date,
                        items = items,
                        totalWorkTime = formatDurationInHHMMToString(totalDayTime)
                    )
                }
                .sortedBy { it.date }
            val totalMonthTime = calculateTotalTime(sortedDays.flatMap { it.items })
            Month(
                name = month.key,
                days = sortedDays,
                totalWorkTimeInHours = formatDurationInHHToString(totalMonthTime)
            )
        }
    return monthList
}
