package com.master.tech.soft.solutions.localmarketapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.master.tech.soft.solutions.localmarketapp.data.model.Product

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleLarge)
            Text("₱${product.price}", style = MaterialTheme.typography.bodyLarge)
            product.description?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}