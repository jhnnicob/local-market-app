package com.master.tech.soft.solutions.localmarketapp.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.master.tech.soft.solutions.localmarketapp.data.model.Category
import com.master.tech.soft.solutions.localmarketapp.data.model.Farmer
import com.master.tech.soft.solutions.localmarketapp.data.model.User
import java.util.Locale

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private var productListener: ListenerRegistration? = null

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _selectedProduct = mutableStateOf<Product?>(null)
    val selectedProduct: State<Product?> = _selectedProduct

    // Sample User
    val dummyUser = User(
        id = "user123",
        name = "Juan Dela Cruz",
        email = "juan@example.com",
    )

    // Sample Farmers
    val dummyFarmers = listOf(
        Farmer(id = "f1", name = "Juan Dela Cruz"),
        Farmer(id = "f2", name = "Maria Santos"),
        Farmer(id = "f3", name = "Pedro Ramos"),
        Farmer(id = "f4", name = "Liza Reyes"),
        Farmer(id = "f5", name = "Carlos Mendoza")
    )

    // Sample Products
    val dummyProducts = listOf(
        Product(id = "p1", name = "Fresh Mango", price = 70.0, imageUrl = "https://example.com/mango.jpg"),
        Product(id = "p2", name = "Organic Banana", price = 50.0, imageUrl = "https://example.com/banana.jpg"),
        Product(id = "p3", name = "Red Tomato", price = 35.0, imageUrl = "https://example.com/tomato.jpg")
    )

    // Sample Categories
    val dummyCategories = listOf(
        Category(id = "c1", name = "Fruits", icon = "🍎"),
        Category(id = "c2", name = "Vegetables", icon = "🥦"),
        Category(id = "c3", name = "Herbs", icon = "🌿")
    )


    init {
        observeProducts()
    }

    private fun observeProducts() {
        productListener = repository.listenToProducts(
            onUpdate = { products ->
                _uiState.value = _uiState.value.copy(
                    products = products,
                    recentProducts = dummyProducts,
                    bestFarmers = dummyFarmers,
                    categories = dummyCategories,
                    errorMessage = null,
                    isLoading = false
                )
            },
            onError = { exception ->
                Log.e("Firestore", "Error listening to products", exception)
                _uiState.value = _uiState.value.copy(
                    errorMessage = exception.message,
                    isLoading = false
                )
            }
        )
    }

    fun stopObservingProducts() {
        productListener?.remove()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val result = repository.getProducts()
                _uiState.value = _uiState.value.copy(products = result, errorMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.addProduct(product)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun loadProductById(id: String) {
        if (_selectedProduct.value?.id == id) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val product = repository.getProductById(id)
                _selectedProduct.value = product
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading product by ID", e)
                _selectedProduct.value = null
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}

data class HomeUiState(
    val user: User? = null,
    val bestFarmers: List<Farmer> = emptyList(),
    val products: List<Product> = emptyList(),
    val recentProducts: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)