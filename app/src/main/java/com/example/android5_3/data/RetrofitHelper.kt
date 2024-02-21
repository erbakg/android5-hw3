package com.example.android5_3.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val baseUrl = "https://pixabay.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
object RetrofitClient {
    val apiService: RetrofitApi by lazy {
        RetrofitHelper.retrofit.create(RetrofitApi::class.java)
    }
}