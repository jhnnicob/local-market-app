package com.master.tech.soft.solutions.localmarketapp.data.remote.api

import com.master.tech.soft.solutions.localmarketapp.data.remote.dto.CartItemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {

    @GET("cart")
    suspend fun getCart(): List<CartItemDto>

    @POST("cart")
    suspend fun addToCart(@Body cartItem: CartItemDto): Response<Unit>

    @DELETE("cart/{productId}")
    suspend fun removeFromCart(@Path("productId") productId: String): Response<Unit>

    @DELETE("cart")
    suspend fun clearCart(): Response<Unit>
}
