package com.florencenjeri.waterreminder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WaterConsumptionDataDao {
    @Query("SELECT * FROM water_taken_data")
    fun getWaterConsumptionData(): LiveData<WaterDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserConsumptionData(waterDataEntity: WaterDataEntity)
}