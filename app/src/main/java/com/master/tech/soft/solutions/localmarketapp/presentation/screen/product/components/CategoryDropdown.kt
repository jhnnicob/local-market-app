package com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.master.tech.soft.solutions.localmarketapp.data.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    isLoading: Boolean,
    error: String?,
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit
) {
    var expanded = remember { mutableStateOf(false) }

    when {
        isLoading -> {
            CircularProgressIndicator()
        }

        error != null -> {
            Text("Error: $error", color = Color.Red)
        }

        else -> {
            ExposedDropdownMenuBox (
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedCategory?.name ?: "Select Category",
                    onValueChange = {},
                    label = { Text("Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded.value)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                onCategorySelected(category)
                                expanded.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}
