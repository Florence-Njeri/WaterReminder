package com.florencenjeri.waterreminder.utils

class SettingsCredentialsValidator {
    private lateinit var username: String
    private lateinit var waterConsumptionGoal: String
    private lateinit var weight: String
    private lateinit var height: String
    private lateinit var sleepingTime: String
    private lateinit var wakeUpTime: String

    fun setCredentials(
        username: String,
        waterConsumptionGoal: String,
        weight: String,
        height: String,
        sleepingTime: String,
        wakeUpTime: String,
        unitsOfMeasure: String
    ) {
        this.username = username
        this.waterConsumptionGoal = waterConsumptionGoal
        this.weight = weight
        this.height = height
        this.sleepingTime = sleepingTime
        this.wakeUpTime = wakeUpTime
    }

    fun isNameValid() = username.length > 4
    fun isConsumptionGoalValid() = waterConsumptionGoal.length > 1
    fun isWeightValid() = weight.length > 2
    fun isHeightValid() = height.length > 2
    fun isSleepingTimeValid() = sleepingTime.isNotBlank()
    fun isWakeUpTimeValid() = wakeUpTime.isNotBlank()

    fun areCredentialsValid() =
        isNameValid() && isConsumptionGoalValid() && isWakeUpTimeValid() && isHeightValid() && isWeightValid() && isSleepingTimeValid()
}