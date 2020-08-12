package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import com.florencenjeri.waterreminder.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val settingsRepository: SettingsRepository) : ViewModel() {
    val profileSettings = MutableLiveData<UserSettingsEntity>()
    fun getProfileSettings(): LiveData<UserSettingsEntity> = profileSettings
    fun getUserSettingsData() = viewModelScope.launch(Dispatchers.IO) {
        profileSettings.value = settingsRepository.retrieveUserSettings()
    }
}