package com.cvealert.cvealert.database

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.cvealert.cvealert.database.cpe.Cpe
import com.cvealert.cvealert.database.cve.Cve
import com.cvealert.cvealert.database.cve.CveDao
import com.cvealert.cvealert.database.relation.CveWithSubscriptionAndCPEs
import com.cvealert.cvealert.database.setting.Setting
import com.cvealert.cvealert.database.setting.SettingDao
import com.cvealert.cvealert.database.subscription.Subscription
import com.cvealert.cvealert.database.subscription.SubscriptionDao

class MyRepository(
    private val settingDao: SettingDao,
    private val subscriptionDao: SubscriptionDao,
    private val cveDao: CveDao
) {

    val getSetting: LiveData<Setting> = settingDao.get()
    val getAllSubscription: LiveData<List<Subscription>> = subscriptionDao.getAll()
    val getAllActiveSubscriptions = subscriptionDao.getAllActive()
    val getAllCves: LiveData<List<Cve>> = cveDao.getAll()
    val selectCVEwithSubscriptionAndCPEs: LiveData<List<CveWithSubscriptionAndCPEs>> =
        cveDao.selectCVEwithSubscription()

    fun deleteCveWithSubscriptionSync(subscription: Subscription) {
        cveDao.deleteCveWithSubscription(subscription.id)
    }

    fun insertSetting(setting: Setting) {
        settingDao.insert(setting)
    }

    fun updateSetting(setting: Setting) {
        settingDao.updateSetting(setting)
    }

    fun getSettingSyn(): Setting? {
        return settingDao.getSync()
    }

    fun insertSubscriptionSync(subscription: Subscription): Int {
        return try {
            subscriptionDao.insert(subscription).toInt()
        } catch (e: SQLiteConstraintException) {
            -1
        }
    }

    fun updateSubscription(subscription: Subscription) {
        try {
            subscriptionDao.update(subscription)
        } catch (e: SQLiteConstraintException) {
        }
    }

    fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.delete(subscription)
    }

    fun insertCvesSync(cves: Iterable<Cve>) {
        cveDao.insertCVEs(cves)
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

    fun deleteCveWherePublishedDateSAfter(time: String) {
        cveDao.deleteCveWherePublishedDateSAfter(time)
    }

    fun countCVEs(): Int {
        return cveDao.count()
    }
}