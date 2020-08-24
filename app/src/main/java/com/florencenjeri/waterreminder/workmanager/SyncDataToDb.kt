package com.florencenjeri.waterreminder.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncDataToDb(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        //Save Data to Room database and clear the water data saved to shared prefs

        return Result.success()
    }
}