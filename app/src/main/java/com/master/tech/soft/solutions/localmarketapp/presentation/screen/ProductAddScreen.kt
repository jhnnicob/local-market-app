package com.master.tech.soft.solutions.localmarketapp.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.HomeViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddScreen(
    onBack: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val inStock = remember { mutableStateOf(true) }

    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Add Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = price.value,
                    onValueChange = { price.value = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = imageUrl.value,
                    onValueChange = { imageUrl.value = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category.value,
                    onValueChange = { category.value = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Checkbox(
                        checked = inStock.value,
                        onCheckedChange = { inStock.value = it }
                    )
                    Text("In Stock")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val priceValue = price.value.toDoubleOrNull()
                        if (name.value.isBlank() || priceValue == null) {
                            Toast.makeText(context, "Name and valid price are required", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val product = Product(
                            id = UUID.randomUUID().toString(),
                            name = name.value,
                            description = description.value,
                            price = priceValue,
                            imageUrl = imageUrl.value,
                            category = category.value,
                            inStock = inStock.value
                        )
                        viewModel.addProduct(product)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Product")
                }

                TextButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Cancel")
                }
            }
        }
    )
}
