package com.florencenjeri.waterreminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(UserSettingsEntity::class)], version = 1)
abstract class UserSettingsDatabase : RoomDatabase() {
    abstract fun userSettingsDao(): UserSettingsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserSettingsDatabase? = null

        fun getDatabase(
            context: Context
        ): UserSettingsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserSettingsDatabase::class.java,
                    NEWS_DB
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        const val NEWS_DB = "user-settings-db"
    }


}