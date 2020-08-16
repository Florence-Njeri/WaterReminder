package com.florencenjeri.waterreminder.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.florencenjeri.waterreminder.App
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.repository.SettingsRepository
import java.util.*
import java.util.concurrent.TimeUnit


class ReminderWorkManager(
    context: Context,
    workerParameters: WorkerParameters,
    private val repository: SettingsRepository
) :
    CoroutineWorker(context, workerParameters) {
    private val TAG_OUTPUT: String = "Interval"

    companion object {
        const val WORKER_ID = "WaterReminderWorkerID"
        val app = App.getAppContext()
    }

    override suspend fun doWork(): Result {
        val settings = repository.retrieveUserFromWorker()
        val wakeUpTime = settings.endTime // 4 AM
        val dueDate = Calendar.getInstance()
        val currentDate = Calendar.getInstance()
        // Set Execution around the users wake up time e.g 4 AM
        dueDate.set(Calendar.HOUR_OF_DAY, wakeUpTime.toInt())
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis.minus(currentDate.timeInMillis)
        Log.d("TimeDiff", timeDiff.toString())
        val dailyWorkRequest = OneTimeWorkRequestBuilder<ReminderWorkManager>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(TAG_OUTPUT)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)

        return try {
            createNotification()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }

    }

    private fun createNotification() {
        val notificationManager = ContextCompat.getSystemService(
            app,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(
            app.getString(R.string.time_to_drink_water_message),
            app
        )
        createChannel(
            app.getString(R.string.drink_water_channel_id),
            app.getString(R.string.drink_water_channel_name)
        )
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                app.getString(R.string.drink_water_channel_description)
            val notificationManager = App.getAppContext().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}