package com.florencenjeri.waterreminder.repository

import com.florencenjeri.waterreminder.prefs.UserPrefsManager

class UserRepositoryImpl(private val sharedPreferenceManager: UserPrefsManager) :
    UserRepository {
    override fun areProfileSettingsConfigured(): Boolean =
        sharedPreferenceManager.isUserSettingsConfigured()

    override fun configuredUserSettings(isConfigured: Boolean) =
        sharedPreferenceManager.setUserSettingsConfig(isConfigured)
}