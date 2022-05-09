package com.example.cvealert.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cvealert.R
import com.example.cvealert.api.model.cves.MyCves
import com.example.cvealert.api.service.NvdServiceInstance
import com.example.cvealert.database.MyDatabase
import com.example.cvealert.database.MyRepository
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.util.Constants
import java.text.SimpleDateFormat
import java.util.*

class WorkerHelper(private val applicationContext: Context, private val TAG: String) {

    private var myRepository: MyRepository = MyRepository(
        MyDatabase.getDatabase(applicationContext).settingDao(),
        MyDatabase.getDatabase(applicationContext).subscriptionDao(),
        MyDatabase.getDatabase(applicationContext).cveDao()
    )

    private fun getApiKey(): String? {
        val setting = myRepository.getSettingSyn()

        if (setting == null || setting.apiKey.isNullOrBlank()) {
            return null
        }

        return setting.apiKey
    }

    fun deleteOldCVEs() {
        myRepository.deleteCveWherePublishedDateAfterSync(Constants.getLastCVEDateTime())
    }

    fun getAndStoreCVEsAndCPEs() {

        val subscriptions = myRepository.getAllActiveSubscriptions
        var numberOfCVEs = myRepository.countCVEs()

        subscriptions.forEach { subscription ->

            if (!storeCVEsAndCPEsByCpeString(subscription.getCPE23URL(), subscription.id)) {
                Log.w(TAG, "failed to store and save CPEs and CVEs for subscription!")
            }
        }

        numberOfCVEs = myRepository.countCVEs() - numberOfCVEs
        if (numberOfCVEs > 1) {
            sendNotification(numberOfCVEs)
        }
    }

    /**
     * also updates CVEs and CPEs thanks to OnConflictStrategy.REPLACE
     */
    fun storeCVEsAndCPEsByCpeString(cpeString: String, subscriptionId: Int): Boolean {

        val getCvesCall = NvdServiceInstance.service.getCVEs(
            resultsPerPage = Constants.RESULTS_PER_PAGE,
            apiKey = getApiKey(),
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


    companion object {
        const val NOTIFICATION_NAME = "cveAlert"
        const val NOTIFICATION_CHANNEL = "cveAlert_channel_01"
    }

    private fun sendNotification(numberOfNewCVEs: Int) {

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_baseline_bug_report_24)
            .setContentTitle("CVE Alert")
            .setContentText("$numberOfNewCVEs new CVEs!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_DEFAULT)

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(1, builder.build())
    }
}