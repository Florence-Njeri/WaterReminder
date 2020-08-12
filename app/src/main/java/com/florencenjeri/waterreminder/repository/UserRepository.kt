package com.florencenjeri.waterreminder.repository

interface UserRepository {
    fun isUserSettingsConfigured(): Boolean
    fun configureUserSettings(isConfigured: Boolean)
}