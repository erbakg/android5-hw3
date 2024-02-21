package com.example.android5_3.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://pixabay.com/api/?key=14823494-c55a9c6593fac158e5442df81&q=yellow+flowers&image_type=photo
interface RetrofitApi {
    @GET("api/")
    fun getImages(
        @Query("key") key: String = "14823494-c55a9c6593fac158e5442df81",
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 3,
        @Query("page") page: Int = 1
    ): Call<ImagesModel>
}