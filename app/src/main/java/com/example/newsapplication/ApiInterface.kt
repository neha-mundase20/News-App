package com.example.newsapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("sources")sources:String,@Query("apiKey")apiKey:String): Response<ApiResponse>
}