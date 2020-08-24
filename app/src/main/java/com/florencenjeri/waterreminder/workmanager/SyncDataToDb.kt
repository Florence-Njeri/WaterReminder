package com.florencenjeri.waterreminder.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.florencenjeri.waterreminder.database.WaterDataEntity
import com.florencenjeri.waterreminder.repository.UserRepository
import com.florencenjeri.waterreminder.repository.WaterConsumptionDataRepository

class SyncDataToDb(
    context: Context,
    workerParameters: WorkerParameters,
    val waterConsumptionDataRepository: WaterConsumptionDataRepository,
    val userRepository: UserRepository
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            //Save Data to Room database
            val waterData = WaterDataEntity(0, userRepository.getNumOfGlassesDrank())
            waterConsumptionDataRepository.insertWaterConsumptionData(waterData)
            //Delete the water data saved to shared prefs
            userRepository.setNumberOfGlassesDrank(0)
            Result.success()
        } catch (error: Exception) {
            Result.failure()
        }
    }
}