package com.florencenjeri.waterreminder.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.florencenjeri.waterreminder.App
import com.florencenjeri.waterreminder.R

class ReminderWorkManager(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {
    companion object {
        const val WORKER_ID = "WaterReminderWorkerID"
        val app = App.getAppContext()
    }

    override suspend fun doWork(): Result {
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