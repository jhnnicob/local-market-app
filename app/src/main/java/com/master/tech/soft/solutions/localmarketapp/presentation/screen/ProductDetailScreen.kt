package com.master.tech.soft.solutions.localmarketapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.master.tech.soft.solutions.localmarketapp.data.model.Product

@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: HomeViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val productState = viewModel.selectedProduct
    val isLoading = viewModel.loading.collectAsState()
    val errorMessage = viewModel.error.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProductById(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        when {
            isLoading.value -> {
                Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                    SkeletonProductDetail()
                }
            }
            errorMessage.value != null -> {
                Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            }
            productState.value != null -> {
                ProductDetailContent(product = productState.value!!)
            }
            else -> {
                Text("Product not found.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun ProductDetailContent(product: Product) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "₱${product.price}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = product.description ?: "No description")
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Category: ${product.category}")
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = if (product.inStock) "✅ In Stock" else "❌ Out of Stock")
        Spacer(modifier = Modifier.height(16.dp))

        if (!product.imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SkeletonProductDetail() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(24.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(20.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(20.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(20.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
    }
}
