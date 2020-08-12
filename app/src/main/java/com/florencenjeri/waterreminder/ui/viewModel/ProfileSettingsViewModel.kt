package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import com.florencenjeri.waterreminder.repository.SettingsRepository
import com.florencenjeri.waterreminder.repository.UserRepository
import com.florencenjeri.waterreminder.utils.CredentialsValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileSettingsViewModel(
    val settingsRepository: SettingsRepository,
    val validator: CredentialsValidator,
    val userRepository: UserRepository
) : ViewModel() {
    private val credentialsValidationState = MutableLiveData<CredentialsValidationState>()

    fun getCredentialsValidationState(): LiveData<CredentialsValidationState> =
        credentialsValidationState

    //Save the users settings in Room DB
    fun saveUserSettings(settings: UserSettingsEntity) = viewModelScope.launch(Dispatchers.IO) {
        settingsRepository.setUserSettings(settings)
    }

    fun checkIfCredentialsAreSet() {
        if (userRepository.areProfileSettingsConfigured()) {
            credentialsValidationState.value = CredentialsValidationState.UserSettingsConfigured
        }
    }

    fun checkCredentials(
        username: String,
        waterConsumptionGoal: String,
        weight: String,
        height: String,
        sleepingTime: String,
        wakeUpTime: String
    ) {
        validator.setUserCredentials(
            username,
            waterConsumptionGoal,
            weight,
            height,
            sleepingTime,
            wakeUpTime
        )
        checkUsername()
        checkConsumptionGoal()
        checkWeight()
        checkHeight()
        checkSleepingTime()
        checkWakeUpTime()
        if (validator.areCredentialsValid()) {
            userRepository.configuredUserSettings(true)
            credentialsValidationState.value = CredentialsValidationState.UserSettingsConfigured
        }
    }

    fun checkUsername() {
        if (validator.isNameValid()) {
            credentialsValidationState.value = CredentialsValidationState.NameValid
        } else {
            credentialsValidationState.value = CredentialsValidationState.NameInvalid
        }
    }

    fun checkConsumptionGoal() {
        if (validator.isConsumptionGoalValid()) {
            credentialsValidationState.value = CredentialsValidationState.ConsumptionGoalValid
        } else {
            credentialsValidationState.value = CredentialsValidationState.ConsumptionGoalInvalid
        }
    }

    fun checkWeight() {
        if (validator.isWeightValid()) {
            credentialsValidationState.value = CredentialsValidationState.WeightValid
        } else {
            credentialsValidationState.value = CredentialsValidationState.WeightInvalid
        }
    }

    fun checkHeight() {
        if (validator.isHeightValid()) {
            credentialsValidationState.value = CredentialsValidationState.HeightValid
        } else {
            credentialsValidationState.value = CredentialsValidationState.HeightInvalid
        }
    }

    fun checkSleepingTime() {
        if (validator.isSleepingTimeValid()) {
            credentialsValidationState.value = CredentialsValidationState.SleepingTimeValid
        } else {
            credentialsValidationState.value = CredentialsValidationState.SleepingTimeInvalid
        }
    }

    fun checkWakeUpTime() {
        if (validator.isWakeUpTimeValid()) {
            credentialsValidationState.value = CredentialsValidationState.WakeUpTimeValid
        } else {
            credentialsValidationState.value = CredentialsValidationState.WakeUpTimeInvalid
        }
    }
}


sealed class CredentialsValidationState {
    object UserSettingsConfigured : CredentialsValidationState()
    object NameValid : CredentialsValidationState()
    object NameInvalid : CredentialsValidationState()
    object ConsumptionGoalValid : CredentialsValidationState()
    object ConsumptionGoalInvalid : CredentialsValidationState()
    object WeightValid : CredentialsValidationState()
    object WeightInvalid : CredentialsValidationState()
    object HeightValid : CredentialsValidationState()
    object HeightInvalid : CredentialsValidationState()
    object SleepingTimeValid : CredentialsValidationState()
    object SleepingTimeInvalid : CredentialsValidationState()
    object WakeUpTimeValid : CredentialsValidationState()
    object WakeUpTimeInvalid : CredentialsValidationState()
}