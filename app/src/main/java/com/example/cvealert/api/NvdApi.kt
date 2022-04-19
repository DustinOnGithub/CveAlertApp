package com.example.cvealert.api

import com.example.cvealert.api.model.CvesResponse
import retrofit2.Response
import retrofit2.http.GET

interface NvdApi {
    @GET("/rest/json/cvessss/1.0")
    suspend fun getCVEs(): Response<CvesResponse>
}