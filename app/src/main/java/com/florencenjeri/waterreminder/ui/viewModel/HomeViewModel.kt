package com.florencenjeri.waterreminder.ui.viewModel

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
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

    fun getUserById(userId: Long) = settingsRepository.getUser(userId)

    fun setUpReminder() {
        workManagerHelper.scheduleWaterReminder()
    }

    fun stopReminder() {
        workManagerHelper.stopReminder()
    }

    fun generateProfileImage(firstLetter: String?): Drawable {
        val generator: ColorGenerator = ColorGenerator.MATERIAL // or use DEFAULT
        // generate random color
        val color1: Int = generator.randomColor
        return TextDrawable.builder()
            .beginConfig()
            .textColor(Color.WHITE)
            .useFont(Typeface.DEFAULT)
            .fontSize(60) /* size in px */
            .bold()
            .toUpperCase()
            .endConfig()
            .buildRoundRect(firstLetter, color1, 100)
    }
}