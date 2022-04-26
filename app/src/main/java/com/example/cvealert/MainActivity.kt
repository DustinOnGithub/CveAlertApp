package com.example.cvealert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cvealert.util.Constants
import com.example.cvealert.workers.DeleteOldWorker
import com.example.cvealert.workers.GetAndStoreWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enqueueWorkers()

        val navController = this.findNavController(R.id.nav_host_fragment)
        val bottomNavigationMenuView = findViewById<BottomNavigationView>(R.id.myNavHostFragment)
        bottomNavigationMenuView.setupWithNavController(navController)

    }

    private fun enqueueWorkers() {
        val workManager = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
        val getAndStoreWorker = PeriodicWorkRequestBuilder<GetAndStoreWorker>(
            Constants.WORKER_REPEAT_INTERVAL, TimeUnit.MINUTES,
            flexTimeInterval = 5, TimeUnit.MINUTES
        )
            .setConstraints(constraints.build())

        val deleteOldWorker = PeriodicWorkRequestBuilder<DeleteOldWorker>(
            1, TimeUnit.DAYS,
            flexTimeInterval = 2, TimeUnit.HOURS
        )

        workManager.enqueue(getAndStoreWorker.build())
        workManager.enqueue(deleteOldWorker.build())
    }
}