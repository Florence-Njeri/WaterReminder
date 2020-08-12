package com.florencenjeri.waterreminder.repository

interface UserRepository {
    fun areProfileSettingsConfigured(): Boolean
    fun configuredUserSettings(isConfigured: Boolean)
}