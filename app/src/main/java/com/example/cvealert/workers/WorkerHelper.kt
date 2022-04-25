package com.example.cvealert.workers

import android.content.Context
import android.util.Log
import com.example.cvealert.Cpe
import com.example.cvealert.api.model.cves.MyCves
import com.example.cvealert.api.service.NvdServiceInstance
import com.example.cvealert.database.MyDatabase
import com.example.cvealert.database.MyRepository
import com.example.cvealert.database.cve.Cve
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

        Log.v(TAG, generatePubStartDate())

        val getCvesCall = NvdServiceInstance.service.getCVEs(
            resultsPerPage = 100,
            apiKey = null,
            cpeMatchString = cpeString, //cpeString,
            pubStartDate = generatePubStartDate(),
            pubEndDate = generatePubEndDate(),
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

    private fun generatePubStartDate(): String {

        val dateTime = ZonedDateTime.now(TimeZone.getDefault().toZoneId()).minusMonths(1)

        return dateTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS 'UTC'xxx")
        )
    }

    private fun generatePubEndDate(): String {

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS 'UTC'XXX")
        return simpleDateFormat.format(Date())
        //2022-01-01T00:00:00:000 UTC+02:00
    }

}