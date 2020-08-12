package com.florencenjeri.waterreminder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings")
    fun retrieveUserSettings(): UserSettingsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUserSettings(userSettings: UserSettingsEntity)
}