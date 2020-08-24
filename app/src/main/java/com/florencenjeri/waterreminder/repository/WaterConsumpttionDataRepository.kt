package com.florencenjeri.waterreminder.repository

import androidx.lifecycle.LiveData
import com.florencenjeri.waterreminder.database.WaterDataEntity

interface WaterConsumpttionDataRepository {
    fun insertWaterConsumptionData(waterDataEntity: WaterDataEntity)

    fun getWaterConsumptionDataFromDb(): LiveData<WaterDataEntity>
}