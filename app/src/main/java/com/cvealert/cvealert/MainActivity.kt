package com.cvealert.cvealert

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cvealert.cvealert.workers.DeleteOldWorker
import com.cvealert.cvealert.workers.GetAndStoreWorker
import com.cvealert.cvealert.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enqueueWorkers()

        val navController = this.findNavController(R.id.nav_host_fragment)
        val bottomNavigationMenuView = findViewById<BottomNavigationView>(R.id.myNavHostFragment)
        bottomNavigationMenuView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(navController.graph)

        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            appBarConfiguration
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return true
    }

    private fun enqueueWorkers() {
        enqueueGetAndStoreWorker()
        enqueueDeleteOldWorker()
    }

    private fun enqueueGetAndStoreWorker() {
        val workManager = WorkManager.getInstance(applicationContext)
        val workStatus = workManager.getWorkInfosByTag(GetAndStoreWorker.TAG).get()
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)

        val getAndStoreWorker = PeriodicWorkRequestBuilder<GetAndStoreWorker>(
            Constants.WORKER_REPEAT_INTERVAL, TimeUnit.MINUTES,
            flexTimeInterval = 5, TimeUnit.MINUTES
        )
            .setConstraints(constraints.build())
            .addTag(GetAndStoreWorker.TAG)

        if (workStatus.count() == 0) {
            workManager.enqueue(getAndStoreWorker.build())
        }
    }

    private fun enqueueDeleteOldWorker() {
        val workManager = WorkManager.getInstance(applicationContext)
        val workStatus = workManager.getWorkInfosByTag(DeleteOldWorker.TAG).get()

        val deleteOldWorker = PeriodicWorkRequestBuilder<DeleteOldWorker>(
            1, TimeUnit.DAYS,
            flexTimeInterval = 2, TimeUnit.HOURS
        ).addTag(DeleteOldWorker.TAG)

        if (workStatus.count() == 0) {
            workManager.enqueue(deleteOldWorker.build())
        }
    }
}