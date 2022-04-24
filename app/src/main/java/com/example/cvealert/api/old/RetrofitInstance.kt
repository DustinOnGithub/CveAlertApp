package com.example.cvealert.api.old

import com.example.cvealert.util.Constants.Companion.BASE_NVD_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_NVD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NvdApi by lazy {
        retrofit.create(NvdApi::class.java)
    }

}