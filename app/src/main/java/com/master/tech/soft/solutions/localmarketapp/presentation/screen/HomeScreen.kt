package com.master.tech.soft.solutions.localmarketapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val products = viewModel.products.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome to Local Market!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            loading.value -> {
                CircularProgressIndicator()
            }

            error.value != null -> {
                Text(
                    text = "Error: ${error.value}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            products.value.isEmpty() -> {
                Text("No products available.")
            }

            else -> {
                LazyColumn {
                    items(products.value) { product ->
                        ProductItem(product = product) {
                            // navController.navigate("product_detail/${product.id}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

