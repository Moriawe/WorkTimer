package com.moriawe.worktimer2.domain.util

import android.util.Log
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.RepositoryResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

class generateMockList @Inject constructor(
    private val repo: TimeRepository
) {

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

    suspend operator fun invoke(itemCount: Int) {
        withContext(Dispatchers.Default) {
            repeat(itemCount) {
                val timeItems = generateRandomTimeItem()
                Log.d("INSERT DATA", "$timeItems")
                repo.insertTimeItem(timeItems)
            }
        }
    }
}

