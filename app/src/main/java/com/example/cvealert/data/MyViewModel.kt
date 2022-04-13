package com.example.cvealert.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<Setting>>
    private val repository: MyRepository

    init {
        val settingDao = MyDatabase.getDatabase(application).settingDao()
        repository = MyRepository(settingDao)
        readAllData = repository.readAllData
    }

}