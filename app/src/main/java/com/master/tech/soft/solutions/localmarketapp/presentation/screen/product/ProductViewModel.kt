package com.master.tech.soft.solutions.localmarketapp.presentation.screen.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.tech.soft.solutions.localmarketapp.data.model.Category
import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.domain.repository.ProductRepository
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.state.CategoryUiState
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _categoryState = MutableStateFlow(CategoryUiState())
    val categoryState: StateFlow<CategoryUiState> = _categoryState.asStateFlow()

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    fun onPriceChange(newPrice: String) {
        _uiState.update { it.copy(price = newPrice) }
    }

    fun onImageUrlChange(newImageUrl: String) {
        _uiState.update { it.copy(imageUrl = newImageUrl) }
    }

    fun onCategoryChange(category: Category) {
        _categoryState.value = _categoryState.value.copy(selectedCategory = category)
    }

    fun onStockChange(inStock: Boolean) {
        _uiState.update { it.copy(inStock = inStock) }
    }

//    init {
//        loadCategories()
//    }

    fun submitProduct(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        val priceValue = state.price.toDoubleOrNull()
        if (state.name.isBlank() || priceValue == null) {
            _uiState.update { it.copy(errorMessage = "Please provide name and valid price") }
            onError("Please provide name and valid price")
            return
        }

        val now = System.currentTimeMillis()
        val product = Product(
            id = UUID.randomUUID().toString(),
            name = state.name,
            description = state.description,
            price = priceValue,
            imageUrl = state.imageUrl,
            category = categoryState.value.selectedCategory?.name,
            inStock = state.inStock,
            createdAt = now,
            updatedAt = now
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, errorMessage = null) }
            try {
                productRepository.addProduct(product)
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Unknown error") }
                onError(e.message ?: "Unknown error")
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }

    fun loadCategories() {

        viewModelScope.launch {
            Log.d("ProductAddBottomSheet", "ProductViewModel Initial _categoryState ${_categoryState.value}")

            try {
                val categories = productRepository.getAllCategory()
                _categoryState.update {
                    it.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
                Log.d("ProductAddBottomSheet", "ProductViewModel Getting categories ${_categoryState.value}")
            } catch (e: Exception) {
                Log.e("ProductAddBottomSheet", "ProductViewModel Error loading categories", e)
                _categoryState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun resetCategoryState() {
        _categoryState.update {
            it.copy(
                isLoading = true,
                error = null,
                categories = emptyList(),
                selectedCategory = null
            )
        }
    }

    fun clearForm() {
        _uiState.value = ProductUiState()
    }
}