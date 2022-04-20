package com.example.cvealert.api

import com.example.cvealert.api.model.cves.Cves
import retrofit2.Response

class Repository {
    suspend fun getCves(
        resultsPerPage: Int,
        apiKey: String?,
        cpeMatchString: String?,
        pubStartDate: String?,
        pubEndDate: String?,
        startIndex: Int?
    ): Response<Cves> {
        return RetrofitInstance.api.getCVEs(
            resultsPerPage = resultsPerPage,
            apiKey = apiKey,
            cpeMatchString = cpeMatchString,
            pubStartDate = pubStartDate,
            pubEndDate = pubEndDate,
            startIndex = startIndex
        )
    }
}