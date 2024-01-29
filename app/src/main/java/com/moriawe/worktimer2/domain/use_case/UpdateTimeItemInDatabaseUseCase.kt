package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.domain.mapper.mapTimeCardItemToTimeItem
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.repository.TimeRepository

class UpdateTimeItemInDatabaseUseCase(
    private val repo: TimeRepository
    ) {

    suspend operator fun invoke(timeCardItem: TimeCardItem) {

            repo.updateTimeItem(
                mapTimeCardItemToTimeItem(timeCardItem)
            )
        }

    }