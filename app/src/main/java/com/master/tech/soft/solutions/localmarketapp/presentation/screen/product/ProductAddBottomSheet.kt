package com.master.tech.soft.solutions.localmarketapp.presentation.screen.product

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.master.tech.soft.solutions.localmarketapp.data.model.Category
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.components.CategoryDropdown
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.components.ProductTextField
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.state.CategoryUiState
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.state.ProductUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val categoryState by viewModel.categoryState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetCategoryState()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            if (!uiState.isSubmitting) {
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
        if (categoryState.isLoading) {
            ProductFormSkeletonLoader()
            Log.d("ProductAddBottomSheet", "Loading categories show ProductFormSkeletonLoader")
        } else {
            Log.d("ProductAddBottomSheet", "Loading categories show ProductAddBottomSheetContent")
            ProductAddBottomSheetContent(
                uiState = uiState,
                categoryState = categoryState,
                onNameChange = viewModel::onNameChange,
                onDescriptionChange = viewModel::onDescriptionChange,
                onPriceChange = viewModel::onPriceChange,
                onImageUrlChange = viewModel::onImageUrlChange,
                onCategorySelected = viewModel::onCategoryChange,
                onStockChange = viewModel::onStockChange,
                onSubmit = {
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
                onCancel = {
                    onDismiss()
                }
            )
        }

    }
}

@Composable
fun ProductAddBottomSheetContent(
    uiState: ProductUiState,
    categoryState: CategoryUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onStockChange: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Add New Product",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(16.dp))

        ProductTextField(
            label = "Name",
            value = uiState.name,
            onValueChange = onNameChange
        )

        ProductTextField(
            label = "Description",
            value = uiState.description,
            onValueChange = onDescriptionChange
        )

        ProductTextField(
            label = "Price",
            value = uiState.price,
            onValueChange = onPriceChange,
            keyboardType = KeyboardType.Number
        )

        ProductTextField(
            label = "Image URL",
            value = uiState.imageUrl,
            onValueChange = onImageUrlChange
        )

        Spacer(Modifier.height(8.dp))

        CategoryDropdown(
            categories = categoryState.categories,
            selectedCategory = categoryState.selectedCategory,
            onCategorySelected = onCategorySelected
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.inStock,
                onCheckedChange = onStockChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Available in stock",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(24.dp))

        AnimatedContent(
            targetState = uiState.isSubmitting,
            label = "SubmitButtonLoadingAnimation"
        ) { isSubmitting ->
            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(36.dp)
                )
            } else {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add Product", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        TextButton(
            onClick = onCancel,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Cancel")
        }
    }
}

@Composable
fun ProductFormSkeletonLoader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Title placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(28.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Spacer(Modifier.height(16.dp))

        // Text field placeholders
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .padding(vertical = 6.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }

        Spacer(Modifier.height(16.dp))

        // Submit button placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    shape = RoundedCornerShape(12.dp)
                )
        )

        Spacer(Modifier.height(8.dp))

        // Cancel button placeholder
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(100.dp)
                .height(24.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    shape = RoundedCornerShape(6.dp)
                )
        )
    }
}



