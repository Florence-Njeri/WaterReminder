package com.florencenjeri.waterreminder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "goal")
    val goal: String,
    @ColumnInfo(name = "start_time")
    val startTime: String,
    @ColumnInfo(name = "end_time")
    val endTime: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "weight")
    val weight: String,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "units_of_measurement")
    val measurementUnits: String
)