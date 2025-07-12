package com.master.tech.soft.solutions.localmarketapp.presentation.screen

import com.master.tech.soft.solutions.localmarketapp.data.model.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)