package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.mapper.mapTimeCardItemToTimeItem
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.repository.TimeRepository

class SaveTimeItemToDatabaseUseCase(
    private val repo: TimeRepository
) {

    suspend operator fun invoke(timeCardItem: TimeCardItem): RepositoryResults {

        val response = try {
            val timeItem = mapTimeCardItemToTimeItem(timeCardItem)
            repo.insertTimeItem(timeItem)
            RepositoryResults.Success(timeItem)
        } catch (exception: Exception) {
            RepositoryResults.Error(R.string.error_add)
        }
        return response
    }
}

// TODO: Add / save / insert
// TODO: End or stop time/item