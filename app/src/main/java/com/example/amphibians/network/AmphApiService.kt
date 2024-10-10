package com.example.amphibians.network

import retrofit2.http.GET

interface AmphApiService{
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibians>
}
