package com.santiagolozada.data.service

import com.santiagolozada.data.models.DetailProductNetwork
import com.santiagolozada.data.models.SearchNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sites/MLA/search")
    suspend fun getItems(
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): SearchNetwork

    @GET("items/{id}")
    suspend fun getProductDetail(@Path("id") id: String): DetailProductNetwork

}