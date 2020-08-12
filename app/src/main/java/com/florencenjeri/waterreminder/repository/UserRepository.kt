package com.florencenjeri.waterreminder.repository

interface UserRepository {
    fun isUserSettingsConfigured(): Boolean
    fun configuredUserSettings(isConfigured: Boolean)
}