package com.moriawe.worktimer2.presentation.timer

import android.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SwipeActionsConfig(
    val threshold: Float,
    val icon: ImageVector,
    val iconTint: Color,
    val background: Color,
    val stayDismissed: Boolean,
    val onDismiss: () -> Unit
)
