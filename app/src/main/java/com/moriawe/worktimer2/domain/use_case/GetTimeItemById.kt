package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import javax.inject.Inject

class GetTimeItemById @Inject constructor(
    private val repo: TimeRepository
) {
    suspend operator fun invoke(id: Int): RepositoryResults<TimeItem> {
        Log.d("GET ITEM", "Fetching id: $id")

        // TODO: Should this error handling be in the repository instead?
        return try {
            val timeItem = repo.getItemById(id)
            Log.d("GET ITEM", "Got item: $timeItem")
            RepositoryResults.Success(timeItem)
        } catch (e: Exception) {
            Log.e("GET ITEM", "Exception - $e")
            RepositoryResults.Error(message = R.string.error_get_item)
        }
    }
}