package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.prefs.UserPrefsManager
import org.koin.dsl.module

val applicationModule = module {
    single { UserPrefsManager(get()) }
}