package com.example.cvealert.api.service

import com.example.cvealert.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NvdServiceInstance {

    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_NVD_URL)
        .build()

    val service: NvdService = retrofit.create(
        NvdService::class.java
    )

}