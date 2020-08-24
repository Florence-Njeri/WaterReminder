package com.florencenjeri.waterreminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(WaterConsumptionDataDao::class)], version = 1)
abstract class WaterDataDatabase : RoomDatabase() {
    abstract fun waterDataDao(): WaterConsumptionDataDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WaterDataDatabase? = null

        fun getDatabase(
            context: Context
        ): WaterDataDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WaterDataDatabase::class.java,
                    WATER_DATA_DB
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        const val WATER_DATA_DB = "water-data-db"
    }
}