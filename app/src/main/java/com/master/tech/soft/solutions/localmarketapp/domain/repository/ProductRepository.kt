package com.master.tech.soft.solutions.localmarketapp.domain.repository

import com.google.firebase.firestore.ListenerRegistration
import com.master.tech.soft.solutions.localmarketapp.data.model.Category
import com.master.tech.soft.solutions.localmarketapp.data.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun addProduct(product: Product): Boolean
    suspend fun updateProduct(product: Product): Boolean
    suspend fun deleteProduct(id: String): Boolean
    suspend fun getRecentlyListedProducts(limit: Int = 10): List<Product>
    suspend fun getAllCategory(): List<Category>
    fun listenToProducts(onUpdate: (List<Product>) -> Unit, onError: (Exception) -> Unit): ListenerRegistration
}