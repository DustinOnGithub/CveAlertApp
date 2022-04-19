package com.example.cvealert.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cvealert.data.setting.Setting
import com.example.cvealert.data.subscription.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val getSetting: LiveData<Setting>
    val getAllSubscriptions: LiveData<List<Subscription>>

    private val repository: MyRepository

    init {
        repository = MyRepository(
            MyDatabase.getDatabase(application).settingDao(),
            MyDatabase.getDatabase(application).subscriptionDao()
        )

        getSetting = repository.getSetting
        getAllSubscriptions = repository.getAllSubscription
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