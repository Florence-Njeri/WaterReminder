package com.florencenjeri.waterreminder.repository

import com.florencenjeri.waterreminder.database.UserSettingsDao
import com.florencenjeri.waterreminder.database.UserSettingsEntity

class SettingsRepositoryImpl(private val userSettingsDao: UserSettingsDao) : SettingsRepository {
    override suspend fun retrieveUserSettings(): UserSettingsEntity =
        userSettingsDao.retrieveUserSettings()

    override suspend fun setUserSettings(userSettings: UserSettingsEntity) =
        userSettingsDao.setUserSettings(userSettings)
}