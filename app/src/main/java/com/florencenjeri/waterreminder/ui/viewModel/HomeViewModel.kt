package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import com.florencenjeri.waterreminder.repository.SettingsRepository
import com.florencenjeri.waterreminder.utils.WorkManagerHelper

class HomeViewModel(
    val settingsRepository: SettingsRepository,
    val workManagerHelper: WorkManagerHelper
) : ViewModel() {
    val profileSettings = MutableLiveData<UserSettingsEntity>()
    fun getProfileSettings(): LiveData<UserSettingsEntity> = profileSettings
    fun getUserSettingsData() = settingsRepository.retrieveUserSettings()

    fun setUpReminder() {
        workManagerHelper.scheduleWaterReminder()
    }

    fun stopReminder() {
        workManagerHelper.stopReminder()
    }
}