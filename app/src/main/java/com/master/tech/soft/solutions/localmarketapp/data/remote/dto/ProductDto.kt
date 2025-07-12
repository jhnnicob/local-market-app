package com.master.tech.soft.solutions.localmarketapp.data.remote.dto

data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val inStock: Boolean
)
