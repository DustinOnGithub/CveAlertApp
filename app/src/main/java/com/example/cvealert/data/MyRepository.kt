package com.example.cvealert.data

import androidx.lifecycle.LiveData

class MyRepository(
    private val settingDao: SettingDao,
    private val subscriptionDao: SubscriptionDao
) {

    val getAllSettings: LiveData<List<Setting>> = settingDao.getAll()
    val getSetting: LiveData<Setting> = settingDao.get()
    val getAllSubscription: LiveData<List<Subscription>> = subscriptionDao.getAll()

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

}