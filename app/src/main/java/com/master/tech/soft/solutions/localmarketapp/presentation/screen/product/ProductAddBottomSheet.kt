package com.master.tech.soft.solutions.localmarketapp.presentation.screen.product

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.master.tech.soft.solutions.localmarketapp.data.model.Category
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.components.CategoryDropdown
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.components.ProductTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddBottomSheet(
    onDismiss: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val uiState = viewModel.uiState.collectAsState()
    val categoryState = viewModel.categoryState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    ModalBottomSheet(
        onDismissRequest = {
            if(!uiState.value.isSubmitting) {
                onDismiss()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        tonalElevation = 8.dp,
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Add New Product",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(16.dp))

            ProductTextField(
                label = "Name",
                value = uiState.value.name,
                onValueChange = { viewModel.onNameChange(it) }
            )

            ProductTextField(
                label = "Description",
                value = uiState.value.description,
                onValueChange = { viewModel.onDescriptionChange(it) }
            )

            ProductTextField(
                label = "Price",
                value = uiState.value.price,
                onValueChange = { viewModel.onPriceChange(it) },
                keyboardType = KeyboardType.Number
            )

            ProductTextField(
                label = "Image URL",
                value = uiState.value.imageUrl,
                onValueChange = { viewModel.onImageUrlChange(it) }
            )
            CategoryDropdown(
                isLoading = categoryState.value.isLoading,
                error = categoryState.value.error,
                categories = categoryState.value.categories,
                selectedCategory = categoryState.value.selectedCategory,
                onCategorySelected = { viewModel.onCategoryChange(it) }
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.value.inStock,
                    onCheckedChange = { viewModel.onStockChange(it) }
                )
                Text("Available in stock", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(24.dp))

            if (uiState.value.isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(36.dp)
                )
            } else {
                Button(
                    onClick = {
                        viewModel.submitProduct(
                            onSuccess = {
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismiss()
                                    }
                                }
                            },
                            onError = { msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add Product", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Cancel")
            }
        }
    }
}
