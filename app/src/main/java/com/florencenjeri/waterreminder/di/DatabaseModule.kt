package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.database.UserSettingsDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { UserSettingsDatabase.getDatabase(get()) }
    single { get<UserSettingsDatabase>().userSettingsDao() }
}