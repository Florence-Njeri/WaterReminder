package com.florencenjeri.waterreminder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings")
    fun retrieveUserSettings(): LiveData<UserSettingsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUserSettings(userSettings: UserSettingsEntity)
}