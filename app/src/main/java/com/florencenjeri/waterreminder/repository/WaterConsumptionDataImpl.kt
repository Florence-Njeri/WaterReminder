package com.florencenjeri.waterreminder.repository

import androidx.lifecycle.LiveData
import com.florencenjeri.waterreminder.database.WaterConsumptionDataDao
import com.florencenjeri.waterreminder.database.WaterDataEntity

class WaterConsumptionDataImpl(val waterConsumptionDao: WaterConsumptionDataDao) :
    WaterConsumptionDataRepository {
    override fun insertWaterConsumptionData(waterDataEntity: WaterDataEntity) {
        waterConsumptionDao.insertUserConsumptionData(waterDataEntity)
    }

    override fun getWaterConsumptionDataFromDb(): LiveData<WaterDataEntity> {
        return waterConsumptionDao.getWaterConsumptionData()
    }
}