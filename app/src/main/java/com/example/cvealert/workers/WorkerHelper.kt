package com.example.cvealert.workers

import android.content.Context
import android.util.Log
import com.example.cvealert.Cpe
import com.example.cvealert.api.model.cves.MyCves
import com.example.cvealert.api.service.NvdServiceInstance
import com.example.cvealert.database.MyDatabase
import com.example.cvealert.database.MyRepository
import com.example.cvealert.database.cve.Cve

class WorkerHelper(private val applicationContext: Context, private val TAG: String) {

    private var myRepository: MyRepository = MyRepository(
        MyDatabase.getDatabase(applicationContext).settingDao(),
        MyDatabase.getDatabase(applicationContext).subscriptionDao(),
        MyDatabase.getDatabase(applicationContext).cveDao()
    )

    fun getAndStoreCVEsAndCPEs() {

        val subscriptions = myRepository.getAllActiveSubscriptions

        subscriptions.forEach { subscription ->

            if (!storeCVEsAndCPEsByCpeString(Cpe.generateStringFromSubscription(subscription))) {
                Log.w(TAG, "failed to store and save CPEs and CVEs for subscription!")
            }
        }

    }

    fun storeCVEsAndCPEsByCpeString(cpeString: String): Boolean {


        val getCvesCall = NvdServiceInstance.service.getCVEs(
            resultsPerPage = 20,
            apiKey = null,
            cpeMatchString = cpeString, //cpeString,
            pubStartDate = null,
            pubEndDate = null,
            startIndex = null
        )

        val response = getCvesCall?.execute()

        //todo: loop this if numberOfPages > 1
        if (response != null && response.isSuccessful && response.body() != null) {

            val myCves: MyCves = response.body()!!
            val generatedDbCves: List<Cve> = myCves.generateDbCves()
            val generatedDbCpes: List<com.example.cvealert.database.cpe.Cpe> =
                myCves.generateDbCPEs()

            myRepository.insertCvesSync(generatedDbCves)
            myRepository.insertCPEsSync(generatedDbCpes)

        } else {
            Log.w(TAG, "NVD-API call failed! Error body: ${response?.errorBody().toString()}")
            Log.w(TAG, "NVD-API call failed! Response code: ${response?.code().toString()}")

            return false
        }

        return true
    }

}