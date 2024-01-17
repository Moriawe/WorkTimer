package com.moriawe.worktimer2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WorkTimerApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}