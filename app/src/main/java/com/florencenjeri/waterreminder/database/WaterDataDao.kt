package com.florencenjeri.waterreminder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WaterDataDao {
    @Query("SELECT * FROM water_taken_data")
    fun getWaterData(): LiveData<WaterDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUserSettings(waterDataEntity: WaterDataEntity)
}