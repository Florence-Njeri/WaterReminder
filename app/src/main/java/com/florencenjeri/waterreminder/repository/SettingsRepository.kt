package com.florencenjeri.waterreminder.repository

import com.florencenjeri.waterreminder.database.UserSettingsEntity

interface SettingsRepository {
    fun retrieveUserSettings(): UserSettingsEntity
    fun setUserSettings(userSettings: UserSettingsEntity)
}