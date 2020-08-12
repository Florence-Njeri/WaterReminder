package com.florencenjeri.waterreminder.di

import com.florencenjeri.waterreminder.ui.viewModel.UserSettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel { UserSettingsViewModel(get()) }
}