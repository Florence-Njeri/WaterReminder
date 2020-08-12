package com.florencenjeri.waterreminder.repository

import com.florencenjeri.waterreminder.database.UserSettingsDao
import com.florencenjeri.waterreminder.database.UserSettingsEntity

class SettingsRepositoryImpl(private val userSettingsDao: UserSettingsDao) : SettingsRepository {
    override fun retrieveUserSettings(): UserSettingsEntity = userSettingsDao.retrieveUserSettings()

    override fun setUserSettings(userSettings: UserSettingsEntity) =
        userSettingsDao.setUserSettings(userSettings)
}