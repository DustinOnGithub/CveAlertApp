package com.example.cvealert.api

import com.example.cvealert.api.model.cves.Cves
import retrofit2.Response
import retrofit2.http.GET

interface NvdApi {
    @GET("/rest/json/cves/1.0")
    suspend fun getCVEs(): Response<Cves>
}