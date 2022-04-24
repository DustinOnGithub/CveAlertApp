package com.example.cvealert.api.old

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvealert.api.model.cves.MyCves
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val cvesResponse: MutableLiveData<Response<MyCves>> = MutableLiveData()

    fun getCves(
        resultsPerPage: Int,
        apiKey: String?,
        cpeMatchString: String?,
        pubStartDate: String?,
        pubEndDate: String?,
        startIndex: Int?
    ) {
        viewModelScope.launch {
            cvesResponse.value = repository.getCves(
                resultsPerPage = resultsPerPage,
                apiKey = apiKey,
                cpeMatchString = cpeMatchString,
                pubStartDate = pubStartDate,
                pubEndDate = pubEndDate,
                startIndex = startIndex
            )
        }
    }

}