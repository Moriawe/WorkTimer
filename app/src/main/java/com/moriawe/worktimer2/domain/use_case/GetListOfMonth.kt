package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.util.convertTimeItemListToMonthList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListOfMonth @Inject constructor(
    private val repo: TimeRepository
) {
    operator fun invoke(): RepositoryResults<Flow<List<Month>>> {
        return try {
            val monthList = repo.getTimeItems()
                .map { timeItemList ->
                    convertTimeItemListToMonthList(timeItemList)
                }
            RepositoryResults.Success(monthList)
        } catch (e: Exception) {
            Log.e("GET LIST OF MONTH", "Exception - $e")
            RepositoryResults.Error(message = R.string.error_get_item)
        }
    }
}