package com.florencenjeri.waterreminder.utils

interface CredentialsValidator {
    fun setUserCredentials(
        username: String,
        waterConsumptionGoal: String,
        weight: String,
        height: String,
        sleepingTime: String,
        wakeUpTime: String
    )

    fun areCredentialsValid(): Boolean
    fun isNameValid(): Boolean
    fun isConsumptionGoalValid(): Boolean
    fun isWeightValid(): Boolean
    fun isHeightValid(): Boolean
    fun isSleepingTimeValid(): Boolean
    fun isWakeUpTimeValid(): Boolean
}