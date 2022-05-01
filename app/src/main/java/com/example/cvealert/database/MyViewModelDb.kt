package com.example.cvealert.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cvealert.database.cpe.Cpe
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.database.relation.CveWithSubscriptionAndCPEs
import com.example.cvealert.database.setting.Setting
import com.example.cvealert.database.subscription.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModelDb(application: Application) : AndroidViewModel(application) {

    val getSetting: LiveData<Setting>
    val getAllSubscriptions: LiveData<List<Subscription>>
    private val getAllCves: LiveData<List<Cve>>
    val selectCVEwithSubscriptionAndCPEs: LiveData<List<CveWithSubscriptionAndCPEs>>

    private val repository: MyRepository = MyRepository(
        MyDatabase.getDatabase(application).settingDao(),
        MyDatabase.getDatabase(application).subscriptionDao(),
        MyDatabase.getDatabase(application).cveDao()
    )

    init {
        getSetting = repository.getSetting
        getAllSubscriptions = repository.getAllSubscription
        getAllCves = repository.getAllCves
        selectCVEwithSubscriptionAndCPEs = repository.selectCVEwithSubscriptionAndCPEs
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

    fun deleteCveWherePublishedDateAfterSync(time: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCveWherePublishedDateSAfter(time)
        }
    }

}