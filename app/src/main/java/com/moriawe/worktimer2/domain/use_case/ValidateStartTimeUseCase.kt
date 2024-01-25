package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.util.parseTimeStamp

class ValidateStartTimeUseCase {

    fun execute(startTime: String): ValidationResult {

        try {
            parseTimeStamp(startTime)
        } catch (e: Exception) {
            return ValidationResult(false, R.string.time_format_error)
        }

        return ValidationResult(true)
    }


}