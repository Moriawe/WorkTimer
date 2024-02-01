package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.domain.repository.TimeRepository
import javax.inject.Inject

class SaveCurrentStartTime @Inject constructor(
    private val repo: TimeRepository
) {
    suspend operator fun invoke(currentStartTime: CurrentStartTime): RepositoryResults<Unit> {

        return try {
            repo.insertCurrentStartTime(currentStartTime)
            RepositoryResults.Success()
        } catch (e: Exception) {
            RepositoryResults.Error(R.string.error_current_time)
        }

    }

}