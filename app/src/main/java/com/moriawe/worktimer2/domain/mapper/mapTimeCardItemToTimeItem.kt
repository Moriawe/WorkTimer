package com.moriawe.worktimer2.domain.mapper

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.util.parseTimeStamp

fun mapTimeCardItemToTimeItem(timeCardItem: TimeCardItem): TimeItem {

    // TODO: Try catch or validate first?
    val startTime = parseTimeStamp(timeCardItem.startTime)
    val stopTime = parseTimeStamp(timeCardItem.endTime)

    return TimeItem(
        startTime = startTime,
        stopTime = stopTime,
        description = timeCardItem.description
    )

}