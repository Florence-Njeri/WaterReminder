package com.florencenjeri.waterreminder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_taken_data")
data class WaterDataEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "num_of_glasses_taken")
    val numOfGlassesTaken: Int
)