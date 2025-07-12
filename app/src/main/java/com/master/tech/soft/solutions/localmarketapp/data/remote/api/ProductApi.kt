package com.master.tech.soft.solutions.localmarketapp.data.remote.api

import com.master.tech.soft.solutions.localmarketapp.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): ProductDto
}