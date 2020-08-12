package com.florencenjeri.waterreminder.utils

class SettingsCredentialsValidator : CredentialsValidator {
    private lateinit var username: String
    private lateinit var waterConsumptionGoal: String
    private lateinit var weight: String
    private lateinit var height: String
    private lateinit var sleepingTime: String
    private lateinit var wakeUpTime: String

    override fun setUserCredentials(
        username: String,
        waterConsumptionGoal: String,
        weight: String,
        height: String,
        sleepingTime: String,
        wakeUpTime: String
    ) {
        this.username = username
        this.waterConsumptionGoal = waterConsumptionGoal
        this.weight = weight
        this.height = height
        this.sleepingTime = sleepingTime
        this.wakeUpTime = wakeUpTime
    }

    override fun isNameValid() = username.length > 4
    override fun isConsumptionGoalValid() = waterConsumptionGoal.length > 1
    override fun isWeightValid() = weight.length > 2
    override fun isHeightValid() = height.length > 2
    override fun isSleepingTimeValid() = sleepingTime.isNotBlank()
    override fun isWakeUpTimeValid() = wakeUpTime.isNotBlank()
    override fun areCredentialsValid() =
        isNameValid() && isConsumptionGoalValid() && isWakeUpTimeValid() && isHeightValid() && isWeightValid() && isSleepingTimeValid()
}