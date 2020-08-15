package com.florencenjeri.waterreminder.utils

import androidx.work.*
import com.florencenjeri.waterreminder.workmanager.ReminderWorkManager
import java.util.concurrent.TimeUnit

open class WorkManagerHelper(private val workManager: WorkManager) {
    //Background work should not delay app start
    private fun scheduleWaterReminder() {
        val constraints = buildConstraints()
        val worker = buildWorker(constraints)
        workManager.enqueueUniquePeriodicWork(
            ReminderWorkManager.WORKER_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )
    }

    private fun buildWorker(constraints: Constraints): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<ReminderWorkManager>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

    }

    private fun buildConstraints(): Constraints {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return constraints
    }

    fun stopReminder() {
        workManager.cancelUniqueWork(ReminderWorkManager.WORKER_ID)
    }
}