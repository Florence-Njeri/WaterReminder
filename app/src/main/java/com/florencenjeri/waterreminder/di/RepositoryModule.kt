package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<WaterConsumptionDataRepository> { WaterConsumptionDataImpl(get()) }
}