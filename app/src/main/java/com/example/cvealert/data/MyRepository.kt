package com.example.cvealert.data

import androidx.lifecycle.LiveData
import com.example.cvealert.data.cve.Cve
import com.example.cvealert.data.cve.CveDao
import com.example.cvealert.data.setting.Setting
import com.example.cvealert.data.setting.SettingDao
import com.example.cvealert.data.subscription.Subscription
import com.example.cvealert.data.subscription.SubscriptionDao

class MyRepository(
    private val settingDao: SettingDao,
    private val subscriptionDao: SubscriptionDao,
    private val cveDao: CveDao
) {

    val getAllSettings: LiveData<List<Setting>> = settingDao.getAll()
    val getSetting: LiveData<Setting> = settingDao.get()
    val getAllSubscription: LiveData<List<Subscription>> = subscriptionDao.getAll()
    val getAllCves: LiveData<List<Cve>> = cveDao.getAll()

    suspend fun insertCve(cve: Cve) {
        cveDao.insert(cve)
    }

    suspend fun updateCve(cve: Cve) {
        cveDao.update(cve)
    }

    suspend fun deleteCve(cve: Cve) {
        cveDao.delete(cve)
    }

    suspend fun insertSetting(setting: Setting) {
        settingDao.insert(setting)
    }

    suspend fun updateSetting(setting: Setting) {
        settingDao.updateSetting(setting)
    }

    suspend fun getSubscriptionById(id: Int): LiveData<Subscription> {
        return subscriptionDao.get(id)
    }

    suspend fun insertSubscription(subscription: Subscription) {
        subscriptionDao.insert(subscription)
    }

    suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.update(subscription)
    }

    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.delete(subscription)
    }

    suspend fun insertCves(cves: Iterable<Cve>) {
        cveDao.insertMultiple(cves)
    }

}