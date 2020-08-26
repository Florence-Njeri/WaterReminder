package com.florencenjeri.waterreminder.repository

interface UserRepository {
    fun areProfileSettingsConfigured(): Boolean
    fun configuredUserSettings(isConfigured: Boolean)
    fun isUserOnboard(): Boolean
    fun setUserOnboarded(isUserOnboard: Boolean)
    fun setNumberOfGlassesDrank(numOfGlasses: Int)
    fun getNumOfGlassesDrank(): Int

}