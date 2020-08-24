package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.database.UserSettingsDatabase
import com.florencenjeri.waterreminder.database.WaterDataDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { UserSettingsDatabase.getDatabase(get()) }

    single { get<UserSettingsDatabase>().userSettingsDao() }

    single { WaterDataDatabase.getDatabase(get()) }

    single { get<WaterDataDatabase>().waterDataDao() }
}