package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.repository.TimeRepository
import javax.inject.Inject

class DeleteTimeItemFromDatabase @Inject constructor(
    private val repo: TimeRepository
) {

    suspend operator fun invoke(timeId: Int): RepositoryResults<Int> {

        val response = try {
            Log.d("DELETE", "Try to delete time item")
            repo.deleteTimeItemWithId(timeId)
            RepositoryResults.Success(timeId)
        } catch (exception: Exception) {
            Log.e("DELETE", "Error - could not delete item")
            RepositoryResults.Error(message = R.string.error_delete)
        }

        return response

    }

}
