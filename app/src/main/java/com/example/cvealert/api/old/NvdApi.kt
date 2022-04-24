package com.example.cvealert.api.old

import com.example.cvealert.api.model.cves.MyCves
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NvdApi {
    @GET("/rest/json/cves/1.0")
    suspend fun getCVEs(
        @Query("resultsPerPage") resultsPerPage: Int,
        @Query("apiKey") apiKey: String?,
        @Query("cpeMatchString") cpeMatchString: String?,
        //pubDate: These parameters specify a collection of CVE that were added to the NVD (i.e., published) during the period
        // if filtering by publication date, both are REQUIRED
        @Query("pubStartDate") pubStartDate: String?,
        @Query("pubEndDate") pubEndDate: String?,
        @Query("startIndex") startIndex: Int?
    ): Response<MyCves>


}