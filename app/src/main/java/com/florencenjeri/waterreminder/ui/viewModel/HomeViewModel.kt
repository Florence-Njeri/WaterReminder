package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.florencenjeri.waterreminder.repository.SettingsRepository
import kotlinx.coroutines.launch

class HomeViewModel(val settingsRepository: SettingsRepository) : ViewModel() {
    fun getUserSettingsData() = viewModelScope.launch {
        settingsRepository.retrieveUserSettings()
    }
}