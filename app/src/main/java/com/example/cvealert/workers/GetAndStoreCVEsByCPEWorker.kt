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

        val repositoryDb: MyRepository = MyRepository(
            MyDatabase.getDatabase(applicationContext).settingDao(),
            MyDatabase.getDatabase(applicationContext).subscriptionDao(),
            MyDatabase.getDatabase(applicationContext).cveDao()
        )

        val getCvesCall = NvdServiceInstance.service.getCVEs(
            resultsPerPage = 20,
            apiKey = null,
            cpeMatchString = null, //cpeString,
            pubStartDate = null,
            pubEndDate = null,
            startIndex = null
        )

        val response = getCvesCall?.execute()

        //todo: loop this if numberOfPages > 1
        if (response != null && response.isSuccessful && response.body() != null) {

            Log.v(TAG, response.body()?.resultsPerPage.toString())
            Log.v(TAG, response.body()?.startIndex.toString())
            Log.v(TAG, response.body()?.totalResults.toString())
            val myCves: MyCves = response.body()!!
            val generatedDbCves: List<Cve> = myCves.generateDbCves()
            val generatedDbCpes: List<com.example.cvealert.database.cpe.Cpe> =
                myCves.generateDbCPEs()


            repositoryDb.insertCvesSync(generatedDbCves)
            repositoryDb.insertCPEsSync(generatedDbCpes)

        } else {
            Log.v("Response", response?.errorBody().toString())
            Log.v("Response", response?.code().toString())
        }

        return Result.success()
    }


}