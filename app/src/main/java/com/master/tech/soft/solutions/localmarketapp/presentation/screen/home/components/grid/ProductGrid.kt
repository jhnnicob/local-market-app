package com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.ProductCard

@Composable
fun ProductGrid(products: List<Product>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (i in products.chunked(3)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                i.forEach { product ->
                    ProductCard(product = product)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
