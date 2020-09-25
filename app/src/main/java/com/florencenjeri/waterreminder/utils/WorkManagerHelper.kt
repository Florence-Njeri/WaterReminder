package com.florencenjeri.waterreminder.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.work.*
import com.florencenjeri.waterreminder.prefs.UserPrefsManager
import com.florencenjeri.waterreminder.workmanager.NotificationWorker
import com.florencenjeri.waterreminder.workmanager.SyncDataToDbWorker
import java.util.*
import java.util.concurrent.TimeUnit

open class WorkManagerHelper(
    private val workManager: WorkManager,
    private val prefs: SharedPreferences
) {
    //Background work should not delay app start
    fun scheduleWaterReminder() {
        val worker = buildWorker()
        workManager.enqueueUniquePeriodicWork(
            NotificationWorker.WORKER_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )
    }

    fun scheduleDailyWaterDaterDatabaseSync() {
        //This is my new default
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 00:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 0)
        dueDate.set(Calendar.MINUTE,0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis.minus(currentDate.timeInMillis)
        val dailyWorkRequest = buildWaterDatabaseSyncWorker(timeDiff)
        workManager.enqueue(dailyWorkRequest)
    }

    private fun buildWaterDatabaseSyncWorker(timeDiff: Long): OneTimeWorkRequest {
        Log.d("TimeDiff",timeDiff.toString())
        return OneTimeWorkRequestBuilder<SyncDataToDbWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag("WATER_DB_DAILY_SYNC").build()
    }

    private fun buildWorker(): PeriodicWorkRequest {
        val delayTime = prefs.getLong(UserPrefsManager.TIME_DELAY_PREFS, 0)
        Log.d("SettingsDelayTimeWorker", delayTime.toString())
        return PeriodicWorkRequestBuilder<NotificationWorker>(
            delayTime,
            TimeUnit.SECONDS
        ).setInitialDelay(delayTime, TimeUnit.SECONDS)
            .build()
    }

    fun stopReminder() {
        workManager.cancelUniqueWork(NotificationWorker.WORKER_ID)
    }
}