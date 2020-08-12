package com.florencenjeri.waterreminder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings")
    suspend fun retrieveUserSettings(): UserSettingsEntity

    @Insert
    suspend fun setUserSettings(userSettings: UserSettingsEntity)
}