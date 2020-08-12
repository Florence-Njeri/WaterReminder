package com.florencenjeri.waterreminder.di

import android.content.Context
import com.florencenjeri.waterreminder.prefs.UserPrefsManager
import com.florencenjeri.waterreminder.ui.view.ProfileSettingsFragment
import com.florencenjeri.waterreminder.utils.CredentialsValidator
import com.florencenjeri.waterreminder.utils.SettingsCredentialsValidator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    single { androidContext().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE) }
    single { UserPrefsManager(get()) }
    scope<ProfileSettingsFragment> {
        scoped<CredentialsValidator> { SettingsCredentialsValidator() }
    }
}
private const val USER_PREFS = ""