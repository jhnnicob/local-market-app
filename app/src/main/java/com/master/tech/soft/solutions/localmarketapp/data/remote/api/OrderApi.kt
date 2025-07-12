package com.master.tech.soft.solutions.localmarketapp.data.remote.api

import com.master.tech.soft.solutions.localmarketapp.data.remote.dto.OrderDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderApi {

    @POST("orders")
    suspend fun checkout(@Body order: OrderDto): Response<Unit>

    @GET("orders")
    suspend fun getOrders(): List<OrderDto>
}
