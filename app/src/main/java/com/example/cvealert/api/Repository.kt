package com.example.cvealert.api

import com.example.cvealert.api.model.CvesResponse
import retrofit2.Response

class Repository {
    suspend fun getCves(): Response<CvesResponse> {
        return RetrofitInstance.api.getCVEs()
    }
}