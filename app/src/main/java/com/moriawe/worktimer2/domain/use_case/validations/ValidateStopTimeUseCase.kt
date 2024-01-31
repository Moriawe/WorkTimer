package com.moriawe.worktimer2.domain.use_case.validations

import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.util.TimeFormatters
import com.moriawe.worktimer2.domain.util.parseDialogTimeStamp
import java.time.LocalTime

class ValidateStopTimeUseCase {

    private fun isValidTimeStampFormat(timeStamp: String): Boolean {
        return try {
            LocalTime.parse(timeStamp, TimeFormatters.dialogFormatter)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isStopTimeAfterStartTime(startTime: LocalTime, stopTime: LocalTime): Boolean {
        return startTime.isBefore(stopTime)
    }

    fun execute(startTime: String, stopTime: String): ValidationResult {
        if (!isValidTimeStampFormat(startTime) || !isValidTimeStampFormat(stopTime)) {
            return ValidationResult(false, R.string.time_format_error)
        }

        val startTimeInLDT = parseDialogTimeStamp(startTime)
        val stopTimeInLDT = parseDialogTimeStamp(stopTime)

        if (!isStopTimeAfterStartTime(startTimeInLDT, stopTimeInLDT)) {
            return ValidationResult(false, R.string.start_end_time_incorrect)
        }

        return ValidationResult(true)
    }
}