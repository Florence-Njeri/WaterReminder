package com.florencenjeri.waterreminder

import android.app.Application
import android.content.Context
import com.florencenjeri.waterreminder.database.UserSettingsDatabase
import com.florencenjeri.waterreminder.di.applicationModule
import com.florencenjeri.waterreminder.di.databaseModule
import com.florencenjeri.waterreminder.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        private lateinit var instance: App
        fun getAppContext(): Context = instance.applicationContext
        val database by lazy {
            UserSettingsDatabase.getDatabase(getAppContext())
        }
        val dao by lazy {
            database.userSettingsDao()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            //Declare my app context
            androidContext(this@App)
            //Declare all my D.I modules
            modules(
                presenterModule, databaseModule, applicationModule
            )
        }

    }
}