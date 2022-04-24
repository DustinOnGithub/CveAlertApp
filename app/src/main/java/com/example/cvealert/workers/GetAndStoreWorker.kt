package com.example.cvealert.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class GetAndStoreWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = GetAndStoreWorker::class.java.simpleName

    override fun doWork(): Result {

        val helper = WorkerHelper(applicationContext, TAG)
        helper.getAndStoreCVEsAndCPEs()

        return Result.success()

    }
}