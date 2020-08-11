package com.florencenjeri.waterreminder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.florencenjeri.waterreminder.data.UserSettings

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings")
    fun retrieveUserSettings(): UserSettings

    @Insert
    fun setUserSettings(userSettings: UserSettings)
}