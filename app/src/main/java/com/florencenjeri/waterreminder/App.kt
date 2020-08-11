package com.florencenjeri.waterreminder

import android.app.Application
import android.content.Context
import com.florencenjeri.waterreminder.database.UserSettingsDatabase

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
    }
}