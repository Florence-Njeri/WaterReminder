package com.florencenjeri.waterreminder

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.florencenjeri.waterreminder.workmanager.ReminderWorkManager
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ReminderWorker {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val config = Configuration.Builder()
            // Set log level to Log.DEBUG to make it easier to debug
            .setMinimumLoggingLevel(Log.DEBUG)
            // Use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testRefreshMainDataWork() {
        val input = workDataOf("One" to 1, "Two" to 2)

        // Create request
        val request = PeriodicWorkRequestBuilder<ReminderWorkManager>(15, TimeUnit.MINUTES)
            .setInputData(input)
            .build()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)

//        val workManagerHelper = WorkManagerHelper(workManager)
//        val workSheduledToRun = workManagerHelper.scheduleWaterReminder()
        workManager.enqueue(request).result.get()
        testDriver?.setPeriodDelayMet(request.id)

        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(request.id).get()
        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.RUNNING))

        // Get the ListenableWorker
//        val worker = TestListenableWorkerBuilder<ReminderWorkManager>(context).build()
//        // Start the work synchronously
//        val result = worker.startWork().get()
//        Assert.assertTrue(result == ListenableWorker.Result.success())

    }
}