package com.florencenjeri.waterreminder

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.florencenjeri.waterreminder.database.UserSettingsDao
import com.florencenjeri.waterreminder.database.UserSettingsDatabase
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSettingsDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: UserSettingsDatabase
    private lateinit var userSettingsDao: UserSettingsDao

    @Before
    fun setup() {
        val context: Context = App.getAppContext()
        try {
            database = Room.inMemoryDatabaseBuilder(context, UserSettingsDatabase::class.java)
                .allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("test", e.message)
        }
        userSettingsDao = database.userSettingsDao()
    }

    @Test
    fun testInsertingAndRetrievingFromDb() = runBlocking {
        userSettingsDao.setUserSettings(
            UserSettingsEntity(
                0,
                "Florence Njeri",
                3000,
                "2c776fa0-9c43-46c0-8f78-88eb54aa733a",
                "https://mma.prnewswire.com/media/1219520/UNDP___SDRPY_Logo.jpg",
                "en",
                55,
                167,
                "https://markets.ft.com/data/announce/detail?dockey=600-202007251404PR_NEWS_USPRX____PH73724-1"
            )
        )

        val settings = userSettingsDao.retrieveUserSettings()

        Assert.assertNotNull(settings)

    }

    @After
    fun tearDown() {
        database.close()
    }
}