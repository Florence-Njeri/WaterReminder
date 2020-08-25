package com.florencenjeri.waterreminder.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.florencenjeri.waterreminder.database.WaterDataEntity
import com.florencenjeri.waterreminder.repository.UserRepository
import com.florencenjeri.waterreminder.repository.WaterConsumptionDataRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import java.util.concurrent.TimeUnit

class SyncDataToDbWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters), KoinComponent {
    private val waterRepository: WaterConsumptionDataRepository by inject()
    private val userRepository: UserRepository by inject()
    override suspend fun doWork(): Result {
        return try {
            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()
            // Set Execution around 00:00:00 AM
            dueDate.set(Calendar.HOUR_OF_DAY, 0)
            dueDate.set(Calendar.MINUTE, 0)
            dueDate.set(Calendar.SECOND, 0)
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            val timeDiff = dueDate.timeInMillis.minus(currentDate.timeInMillis)
            Log.d("TimeDiff", timeDiff.toString())
            val dailyWorkRequest = OneTimeWorkRequestBuilder<SyncDataToDbWorker>()
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag("WATER_DB_DAILY_SYNC")
                .build()
            WorkManager.getInstance(applicationContext)
                .enqueue(dailyWorkRequest)
            //Save Data to Room database
            val waterData = WaterDataEntity(0, userRepository.getNumOfGlassesDrank())
            waterRepository.insertWaterConsumptionData(waterData)
            //Delete the water data saved to shared prefs
            userRepository.clearWaterData()
            Log.d("WaterAfterClear", userRepository.getNumOfGlassesDrank().toString())
            Result.success()
        } catch (error: Exception) {
            error.printStackTrace()
            Result.failure()
        }
    }
}