package com.example.cvealert.data

import androidx.lifecycle.LiveData

class MyRepository(private val settingDao: SettingDao) {

    val readAllData: LiveData<List<Setting>> = settingDao.getAll()
    val readSetting: LiveData<Setting> = settingDao.get()

    suspend fun addSetting(setting: Setting) {
        settingDao.insert(setting)
    }

    suspend fun updateSetting(setting: Setting) {
        settingDao.updateSetting(setting)
    }
}