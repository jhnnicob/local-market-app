package com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.state

import com.master.tech.soft.solutions.localmarketapp.data.model.Category

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)