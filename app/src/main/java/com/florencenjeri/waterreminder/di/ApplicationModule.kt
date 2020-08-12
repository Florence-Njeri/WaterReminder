package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.prefs.UserPrefsManager
import com.florencenjeri.waterreminder.ui.view.ProfileSettingsFragment
import com.florencenjeri.waterreminder.utils.CredentialsValidator
import com.florencenjeri.waterreminder.utils.SettingsCredentialsValidator
import org.koin.dsl.module

val applicationModule = module {
    single { UserPrefsManager(get()) }
    scope<ProfileSettingsFragment> {
        scoped<CredentialsValidator> { SettingsCredentialsValidator() }
    }
}