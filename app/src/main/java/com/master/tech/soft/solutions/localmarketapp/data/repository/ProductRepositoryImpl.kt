package com.master.tech.soft.solutions.localmarketapp.data.repository

import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.data.remote.api.ProductApi
import com.master.tech.soft.solutions.localmarketapp.domain.mapper.toDomain
import com.master.tech.soft.solutions.localmarketapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
     private val api: ProductApi // Temporarily commented out
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "1",
                name = "Organic Bananas",
                description = "Fresh organic bananas from the farm.",
                price = 2.99,
                imageUrl = "https://via.placeholder.com/150",
                inStock = true
            ),
            Product(
                id = "2",
                name = "Whole Wheat Bread",
                description = "Healthy whole wheat bread, baked daily.",
                price = 3.49,
                imageUrl = "https://via.placeholder.com/150",
                inStock = true
            ),
            Product(
                id = "3",
                name = "Fresh Milk",
                description = "Locally produced whole milk.",
                price = 1.99,
                imageUrl = "https://via.placeholder.com/150",
                inStock = false
            )
        )
    }

    override suspend fun getProductById(id: String): Product {
        return getProducts().firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Product with id $id not found.")
    }
}
