package com.example.cvealert.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class GetAndStoreCVEsByCPEWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    companion object {
        private val TAG = GetAndStoreCVEsByCPEWorker::class.simpleName
    }

    override fun doWork(): Result {

        val helper = WorkerHelper(applicationContext, TAG!!)

        if (inputData.getString("cpe_string") == null) {
            Log.w(TAG, "missing input data cpe_string")
            return Result.failure()
        }

        if (inputData.getString("setting_id") == null) {
            Log.w(TAG, "missing input setting_id")
            return Result.failure()
        }

        if (helper.storeCVEsAndCPEsByCpeString(
                inputData.getString("cpe_string")!!,
                inputData.getString("setting_id")!!.toInt()
            )
        ) {
            Log.i(TAG, "success")
            return Result.success()
        }

        Log.i(TAG, "failure")
        return Result.failure()
    }


}