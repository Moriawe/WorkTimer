package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository

class DeleteTimeItemFromDatabaseUseCase(
    private val repo: TimeRepository
) {

    suspend operator fun invoke(timeItem: TimeItem) {

        repo.deleteTimeItem(timeItem)
    }

}
