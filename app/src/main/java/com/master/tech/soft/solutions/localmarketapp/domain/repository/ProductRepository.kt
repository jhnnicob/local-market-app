package com.master.tech.soft.solutions.localmarketapp.domain.repository

import com.master.tech.soft.solutions.localmarketapp.data.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product
}