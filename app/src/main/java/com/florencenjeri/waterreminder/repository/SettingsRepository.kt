package com.florencenjeri.waterreminder.repository

import androidx.lifecycle.LiveData
import com.florencenjeri.waterreminder.database.UserSettingsEntity

interface SettingsRepository {
    fun retrieveUserSettings(): LiveData<UserSettingsEntity>
    fun getUser(id: Long): LiveData<UserSettingsEntity>
    suspend fun setUserSettings(userSettings: UserSettingsEntity)
    fun retrieveUserFromWorker(): UserSettingsEntity
}