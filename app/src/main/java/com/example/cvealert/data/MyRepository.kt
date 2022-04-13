package com.example.cvealert.data

import androidx.lifecycle.LiveData

class MyRepository(private val settingDao: SettingDao) {

    val readAllData: LiveData<List<Setting>> = settingDao.getAll()

    suspend fun addSetting(setting: Setting) {
        settingDao.insert(setting)
    }
}