package com.example.cvealert.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvealert.api.model.CvesResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<CvesResponse>> = MutableLiveData()

    fun getCves() {
        viewModelScope.launch {
            myResponse.value = repository.getCves()
        }
    }

}