package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import javax.inject.Inject

class UpdateTimeItemInDatabase @Inject constructor(
    private val repo: TimeRepository
    ) {

    suspend operator fun invoke(timeItem: TimeItem): RepositoryResults<TimeItem> {

        val response = try {
            repo.updateTimeItem(timeItem)
            RepositoryResults.Success(timeItem)
        } catch (exception: Exception) {
            RepositoryResults.Error(R.string.error_update)
        }
            return response
        }
    }