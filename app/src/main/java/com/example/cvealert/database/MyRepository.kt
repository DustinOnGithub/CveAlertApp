package com.example.cvealert.database

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.example.cvealert.database.cpe.Cpe
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.database.cve.CveDao
import com.example.cvealert.database.setting.Setting
import com.example.cvealert.database.setting.SettingDao
import com.example.cvealert.database.subscription.Subscription
import com.example.cvealert.database.subscription.SubscriptionDao

class MyRepository(
    private val settingDao: SettingDao,
    private val subscriptionDao: SubscriptionDao,
    private val cveDao: CveDao
) {

    val getAllSettings: LiveData<List<Setting>> = settingDao.getAll()
    val getSetting: LiveData<Setting> = settingDao.get()
    val getAllSubscription: LiveData<List<Subscription>> = subscriptionDao.getAll()
    val getAllActiveSubscriptions = subscriptionDao.getAllActive()
    val getAllCves: LiveData<List<Cve>> = cveDao.getAll()

    suspend fun insertCve(cve: Cve) {
        cveDao.insertCVE(cve)
    }

    suspend fun updateCve(cve: Cve) {
        cveDao.update(cve)
    }

    suspend fun deleteCve(cve: Cve) {
        cveDao.delete(cve)
    }

    fun deleteCveWithSubscriptionSync(subscription: Subscription) {
        cveDao.deleteCveWithSubscription(subscription.id)
    }

    suspend fun insertSetting(setting: Setting) {
        settingDao.insert(setting)
    }

    suspend fun updateSetting(setting: Setting) {
        settingDao.updateSetting(setting)
    }

    fun getSettingSyn(): Setting? {
        return settingDao.getSync()
    }

    suspend fun getSubscriptionById(id: Int): LiveData<Subscription> {
        return subscriptionDao.get(id)
    }

    suspend fun insertSubscription(subscription: Subscription) {
        insertSubscriptionSync(subscription)
    }

    fun insertSubscriptionSync(subscription: Subscription): Int {
        try {
            return subscriptionDao.insert(subscription).toInt()
        } catch (e: SQLiteConstraintException) {
            return -1
        }
    }

    suspend fun updateSubscription(subscription: Subscription) {
        try {
            subscriptionDao.update(subscription)
        } catch (e: SQLiteConstraintException) {
        }
    }

    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.delete(subscription)
    }

    suspend fun insertCves(cves: Iterable<Cve>) {
        cveDao.insertCVEs(cves)
    }

    fun insertCvesSync(cves: Iterable<Cve>) {
        cveDao.insertCVEs(cves)
    }

    suspend fun insertCPEs(cpes: Iterable<Cpe>) {
        try {
            cveDao.insertCPEs(cpes)
        } catch (e: SQLiteConstraintException) {
        }
    }

    fun insertCPEsSync(cpes: Iterable<Cpe>) {
        try {
            cveDao.insertCPEs(cpes)
        } catch (e: SQLiteConstraintException) {
        }
    }

    fun deleteCveWherePublishedDateAfterSync(dateTime: String) {
        cveDao.deleteCveWherePublishedDateSAfter(dateTime)
    }

    suspend fun deleteCveWherePublishedDateSAfter(time: String) {
        cveDao.deleteCveWherePublishedDateSAfter(time)
    }

    fun countCVEs(): Int {
        return cveDao.count()
    }
}