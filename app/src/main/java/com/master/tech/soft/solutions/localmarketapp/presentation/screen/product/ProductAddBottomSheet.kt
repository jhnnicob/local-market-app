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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.HomeViewModel
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.components.ProductTextField
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddBottomSheet(
    onDismiss: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // States
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val inStock = remember { mutableStateOf(true) }

    // Focus Requesters for keyboard navigation
    val focusName = remember { FocusRequester() }
    val focusDesc = remember { FocusRequester() }
    val focusPrice = remember { FocusRequester() }
    val focusImage = remember { FocusRequester() }
    val focusCategory = remember { FocusRequester() }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
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
                value = name.value,
                onValueChange = { name.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusName),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions (
                    onNext = { focusDesc.requestFocus() }
                )
            )

            ProductTextField(
                label = "Description",
                value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusDesc),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusPrice.requestFocus() }
                )
            )

            ProductTextField(
                label = "Price",
                value = price.value,
                onValueChange = { price.value = it },
                keyboardType = KeyboardType.Number,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusPrice),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusImage.requestFocus() }
                )
            )

            ProductTextField(
                label = "Image URL",
                value = imageUrl.value,
                onValueChange = { imageUrl.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusImage),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusCategory.requestFocus() }
                )
            )

            ProductTextField(
                label = "Category",
                value = category.value,
                onValueChange = { category.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusCategory),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Optionally hide keyboard or auto submit
                    }
                )
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = inStock.value,
                    onCheckedChange = { inStock.value = it }
                )
                Text("Available in stock", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val priceValue = price.value.toDoubleOrNull()
                    if (name.value.isBlank() || priceValue == null) {
                        Toast.makeText(
                            context,
                            "Please provide name and valid price",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val now = System.currentTimeMillis()
                    val product = Product(
                        id = UUID.randomUUID().toString(),
                        name = name.value,
                        description = description.value,
                        price = priceValue,
                        imageUrl = imageUrl.value,
                        category = category.value,
                        inStock = inStock.value,
                        createdAt = now,
                        updatedAt = now
                    )

                    viewModel.addProduct(product)
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add Product", style = MaterialTheme.typography.labelLarge)
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

