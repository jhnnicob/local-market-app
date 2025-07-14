package com.master.tech.soft.solutions.localmarketapp.data.model

data class Product(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val price: Double = 0.0,
    val imageUrl: String? = null,
    val category: String? = null,
    val inStock: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)