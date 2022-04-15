package com.example.cvealert.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Setting>>
    val readSetting: LiveData<Setting>
    private val repository: MyRepository

    init {
        val settingDao = MyDatabase.getDatabase(application).settingDao()
        repository = MyRepository(settingDao)
        readAllData = repository.readAllData
        readSetting = repository.readSetting
    }

    fun addSetting(setting: Setting) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSetting(setting)
        }
    }

    fun updateSetting(setting: Setting) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSetting(setting)
        }
    }

}