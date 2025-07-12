package com.master.tech.soft.solutions.localmarketapp.data.remote.dto

data class OrderDto(
    val orderId: String,
    val items: List<CartItemDto>,
    val totalAmount: Double,
    val date: String,
    val status: String
)
