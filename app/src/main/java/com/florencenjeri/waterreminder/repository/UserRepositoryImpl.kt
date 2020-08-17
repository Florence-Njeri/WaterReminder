package com.florencenjeri.waterreminder.repository

import com.florencenjeri.waterreminder.prefs.UserPrefsManager

class UserRepositoryImpl(private val sharedPreferenceManager: UserPrefsManager) :
    UserRepository {
    override fun areProfileSettingsConfigured(): Boolean =
        sharedPreferenceManager.isUserSettingsConfigured()

    override fun configuredUserSettings(isConfigured: Boolean) =
        sharedPreferenceManager.setUserSettingsConfig(isConfigured)

    override fun isUserOnboard(): Boolean = sharedPreferenceManager.isUserOnboarded()

    override fun setUserOnboarded(isUserOnboard: Boolean) =
        sharedPreferenceManager.setUserOnboarded(isUserOnboard)
}