package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentStartTime @Inject constructor(
    private val repo: TimeRepository
) {

    operator fun invoke(): Flow<CurrentStartTime?> {
        return repo.getCurrentStartTime()
    }

}


/*
    operator fun invoke(): RepositoryResults<Flow<CurrentStartTime?>> {
        Log.d("START TIME", "Getting current start time")

        return try {
            val currentStartTime = repo.getCurrentStartTime()
            RepositoryResults.Success(currentStartTime)
        } catch (e: Exception) {
            Log.e("Get start time", "Exception - $e")
            RepositoryResults.Error(message = R.string.error_current_time)
        }
    }
 */