package com.example.cvealert.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cvealert.data.cve.Cve
import com.example.cvealert.data.setting.Setting
import com.example.cvealert.data.subscription.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val getSetting: LiveData<Setting>
    val getAllSubscriptions: LiveData<List<Subscription>>
    val getAllCves: LiveData<List<Cve>>

    private val repository: MyRepository = MyRepository(
        MyDatabase.getDatabase(application).settingDao(),
        MyDatabase.getDatabase(application).subscriptionDao(),
        MyDatabase.getDatabase(application).cveDao()
    )

    init {
        getSetting = repository.getSetting
        getAllSubscriptions = repository.getAllSubscription
        getAllCves = repository.getAllCves
    }

    fun insertCve(cve: Cve) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCve(cve)
        }
    }

    fun insertCves(cves: Iterable<Cve>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCves(cves)
        }
    }

    fun updateCve(cve: Cve) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCve(cve)
        }
    }

    fun deleteCve(cve: Cve) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCve(cve)
        }
    }

    fun insertSetting(setting: Setting) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSetting(setting)
        }
    }

    fun updateSetting(setting: Setting) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSetting(setting)
        }
    }

    suspend fun getSubscriptionById(id: Int): LiveData<Subscription> {
        return repository.getSubscriptionById(id)
    }

    fun insertSubscription(subscription: Subscription) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubscription(subscription)
        }
    }

    fun updateSubscription(subscription: Subscription) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubscription(subscription)
        }
    }

    fun deleteSubscription(subscription: Subscription) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSubscription(subscription)
        }
    }

}