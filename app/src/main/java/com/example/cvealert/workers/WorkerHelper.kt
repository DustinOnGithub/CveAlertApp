package com.example.cvealert.workers

import android.content.Context
import android.util.Log
import com.example.cvealert.Cpe
import com.example.cvealert.api.model.cves.MyCves
import com.example.cvealert.api.service.NvdServiceInstance
import com.example.cvealert.database.MyDatabase
import com.example.cvealert.database.MyRepository
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.database.setting.Setting
import com.example.cvealert.util.Constants
import java.text.SimpleDateFormat
import java.util.*

class WorkerHelper(val applicationContext: Context, private val TAG: String) {

    private var setting: Setting? = null

    private var myRepository: MyRepository = MyRepository(
        MyDatabase.getDatabase(applicationContext).settingDao(),
        MyDatabase.getDatabase(applicationContext).subscriptionDao(),
        MyDatabase.getDatabase(applicationContext).cveDao()
    )

    private fun getSetting(): Setting? {
        if (setting == null) {
            setting = myRepository.getSettingSyn()
        }
        return setting
    }

    fun deleteOldCVEs() {
        myRepository.deleteCveWherePublishedDateAfterSync(Constants.getLastCVEDateTime())
    }

    fun getAndStoreCVEsAndCPEs() {

        val subscriptions = myRepository.getAllActiveSubscriptions

        subscriptions.forEach { subscription ->

            if (!storeCVEsAndCPEsByCpeString(
                    Cpe.generateStringFromSubscription(subscription), subscription.id
                )
            ) {
                Log.w(TAG, "failed to store and save CPEs and CVEs for subscription!")
            }
        }

    }

    /**
     * also updates CVEs and CPEs thanks to OnConflictStrategy.REPLACE
     */
    fun storeCVEsAndCPEsByCpeString(cpeString: String, subscriptionId: Int): Boolean {

        val getCvesCall = NvdServiceInstance.service.getCVEs(
            resultsPerPage = 100,
            apiKey = getSetting()?.apiKey,
            cpeMatchString = cpeString,
            pubStartDate = Constants.getLastCVEDateTime(),
            pubEndDate = generatePubEndDate(),
            startIndex = null
        )

        val response = getCvesCall?.execute()

        //todo: loop this if numberOfPages > 1
        if (response != null && response.isSuccessful && response.body() != null) {

            val myCves: MyCves = response.body()!!

            if (myCves.error != null) {

                Log.w(TAG, "response (error) from NVD: " + myCves.error)
                Log.w(TAG, "response (message) from NVD: " + myCves.message)

                return false
            }

            val generatedDbCves: List<Cve> = myCves.generateDbCves(subscriptionId)
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

    private fun generatePubEndDate(): String {

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS 'UTC'XXX")
        return simpleDateFormat.format(Date())
        //2022-01-01T00:00:00:000 UTC+02:00
    }

}