package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.florencenjeri.waterreminder.database.UserSettingsDao
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserSettingsViewModel(val userSettingsDao: UserSettingsDao) : ViewModel() {
    private val settingsEntity = MutableLiveData<UserSettingsEntity>()
    fun saveUserSettings(settings: UserSettingsEntity) = viewModelScope.launch(Dispatchers.IO) {
        userSettingsDao.setUserSettings(settings)
    }
}