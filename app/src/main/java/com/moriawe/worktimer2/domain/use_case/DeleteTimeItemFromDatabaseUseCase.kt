package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository

class DeleteTimeItemFromDatabaseUseCase(
    private val repo: TimeRepository
) {

    suspend operator fun invoke(timeItem: TimeItem): RepositoryResults {

        val response = try {
            repo.deleteTimeItem(timeItem)
            RepositoryResults.Success(timeItem)
        } catch (error: Exception) {
            RepositoryResults.Error(message = R.string.error_delete)
        }

        return response

    }

}
