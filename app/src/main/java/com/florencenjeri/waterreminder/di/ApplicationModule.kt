package com.florencenjeri.waterreminder.di

import android.content.Context
import androidx.work.WorkManager
import com.florencenjeri.waterreminder.prefs.UserPrefsManager
import com.florencenjeri.waterreminder.ui.view.ProfileSettingsFragment
import com.florencenjeri.waterreminder.utils.CredentialsValidator
import com.florencenjeri.waterreminder.utils.SettingsCredentialsValidator
import com.florencenjeri.waterreminder.utils.WorkManagerHelper
import com.florencenjeri.waterreminder.workmanager.ReminderWorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    single { androidContext().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE) }
    single { UserPrefsManager(get()) }
    scope<ProfileSettingsFragment> {
        scoped<CredentialsValidator> { SettingsCredentialsValidator() }
    }
    //WorkManager
    single { WorkManager.getInstance(get()) }
    single { WorkManagerHelper(get()) }
    single { ReminderWorkManager(androidContext(), get()) }
}
private const val USER_PREFS = ""