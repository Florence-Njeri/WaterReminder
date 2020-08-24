package com.florencenjeri.waterreminder.repository

import androidx.lifecycle.LiveData
import com.florencenjeri.waterreminder.database.WaterDataEntity

interface WaterConsumptionDataRepository {
    fun insertWaterConsumptionData(waterDataEntity: WaterDataEntity)

    fun getWaterConsumptionDataFromDb(): LiveData<WaterDataEntity>
}