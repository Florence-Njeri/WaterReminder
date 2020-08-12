package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import com.florencenjeri.waterreminder.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserSettingsViewModel(val settingsRepository: SettingsRepository) : ViewModel() {
    private val settingsEntity = MutableLiveData<UserSettingsEntity>()
    fun saveUserSettings(settings: UserSettingsEntity) = viewModelScope.launch(Dispatchers.IO) {
        settingsRepository.setUserSettings(settings)
    }
}