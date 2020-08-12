package com.florencenjeri.waterreminder.repository

import com.florencenjeri.waterreminder.database.UserSettingsEntity

interface SettingsRepository {
    suspend fun retrieveUserSettings(): UserSettingsEntity
    suspend fun setUserSettings(userSettings: UserSettingsEntity)
}