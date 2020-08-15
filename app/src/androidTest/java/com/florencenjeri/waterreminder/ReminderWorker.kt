package com.florencenjeri.waterreminder

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.florencenjeri.waterreminder.workmanager.ReminderWorkManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReminderWorker {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testRefreshMainDataWork() {
        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<ReminderWorkManager>(context).build()
        // Start the work synchronously
        val result = worker.startWork().get()
        Assert.assertTrue(result == ListenableWorker.Result.success())
    }
}