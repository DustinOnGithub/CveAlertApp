package com.example.cvealert.api

import com.example.cvealert.api.model.cves.Cves
import retrofit2.Response

class Repository {
    suspend fun getCves(): Response<Cves> {
        return RetrofitInstance.api.getCVEs()
    }
}