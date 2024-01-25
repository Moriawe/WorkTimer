package com.moriawe.worktimer2.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: Int? = null
)
