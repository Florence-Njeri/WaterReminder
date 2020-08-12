package com.florencenjeri.waterreminder.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.florencenjeri.waterreminder.database.UserSettingsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val dao: UserSettingsDao) : ViewModel() {
    fun getUserSettingsData() = viewModelScope.launch(Dispatchers.IO) {
        dao.retrieveUserSettings()
    }
}