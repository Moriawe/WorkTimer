package com.moriawe.worktimer2.domain.util

import android.util.Log
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random

// -*- FOR MAKING A MOCK LIST TO THE DATABASE -*- //
fun generateRandomTimeItem(): TimeItem {
    val currentDateTime = LocalDateTime.now()

    val randomDate = LocalDateTime.of(
        currentDateTime.year,
        Random.nextInt(1, 13),
        Random.nextInt(1, 29),
        Random.nextInt(0, 24),
        Random.nextInt(0, 60),
        Random.nextInt(0, 60)
    )

    val startTime = randomDate
    val endTime = startTime.plusHours(Random.nextLong(1, 9))
    val description = "" // Empty string for description

    return TimeItem(
        startTime = startTime,
        stopTime = endTime,
        description = description
    )
}

suspend fun generateAndInsertMockTimeItemsIntoDatabase(repo: TimeRepository, itemCount: Int) {
    withContext(Dispatchers.Default) {
        repeat(itemCount) {
            val timeItems = generateRandomTimeItem()
            Log.d("INSERT DATA", "$timeItems")
            repo.insertTimeItem(timeItems)
        }
    }
}