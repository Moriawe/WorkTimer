package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.repository.TimeRepository
import javax.inject.Inject

class GetTimeItemByIdUseCase @Inject constructor(
    private val repo: TimeRepository
) {
    suspend operator fun invoke(id: Int): RepositoryResults {
        Log.d("GET ITEM", "Fetching id: $id")

        return try {
            val timeItem = repo.getItemById(id)
            Log.d("GET ITEM", "Got item: $timeItem")
            RepositoryResults.Success(timeItem)
        } catch (exception: Exception) {
            RepositoryResults.Error(message = R.string.error_get_item)
        }
    }
}