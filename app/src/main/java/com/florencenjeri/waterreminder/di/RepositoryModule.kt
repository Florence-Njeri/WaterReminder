package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.repository.SettingsRepository
import com.florencenjeri.waterreminder.repository.SettingsRepositoryImpl
import com.florencenjeri.waterreminder.repository.UserRepository
import com.florencenjeri.waterreminder.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}