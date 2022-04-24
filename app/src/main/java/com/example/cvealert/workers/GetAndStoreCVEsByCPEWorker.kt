package com.example.cvealert.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cvealert.api.model.cves.MyCves
import com.example.cvealert.api.service.NvdServiceInstance
import com.example.cvealert.database.MyDatabase
import com.example.cvealert.database.MyRepository
import com.example.cvealert.database.cve.Cve

class GetAndStoreCVEsByCPEWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val TAG = GetAndStoreCVEsByCPEWorker::class.simpleName

    override fun doWork(): Result {

        val helper = WorkerHelper(applicationContext, TAG!!)

        if (inputData.getString("cpe_string") == null) {
            Log.w(TAG, "missing input data cpe_string")
            return Result.failure()
        }

        if (helper.storeCVEsAndCPEsByCpeString(inputData.getString("cpe_string")!!)) {
            return Result.success()
        }

        return Result.failure()
    }


}