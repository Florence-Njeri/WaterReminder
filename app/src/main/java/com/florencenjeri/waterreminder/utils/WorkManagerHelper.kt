package com.florencenjeri.waterreminder.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.florencenjeri.waterreminder.prefs.UserPrefsManager
import com.florencenjeri.waterreminder.workmanager.ReminderWorkManager
import java.util.concurrent.TimeUnit

open class WorkManagerHelper(
    private val workManager: WorkManager,
    private val prefs: SharedPreferences
) {
    //Background work should not delay app start
    fun scheduleWaterReminder() {
        val worker = buildWorker()
        workManager.enqueueUniquePeriodicWork(
            ReminderWorkManager.WORKER_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )
    }

    private fun buildWorker(): PeriodicWorkRequest {
        val delayTime = prefs.getLong(UserPrefsManager.TIME_DELAY_PREFS, 0)
        Log.d("SettingsDelayTimeWorker", delayTime.toString())
        return PeriodicWorkRequestBuilder<ReminderWorkManager>(
            delayTime,
            TimeUnit.HOURS
        ).setInitialDelay(delayTime, TimeUnit.HOURS)
            .build()
    }

    fun stopReminder() {
        workManager.cancelUniqueWork(ReminderWorkManager.WORKER_ID)
    }
}