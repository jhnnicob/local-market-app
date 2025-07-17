package com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.state

data class ProductUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val inStock: Boolean = true,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
)
