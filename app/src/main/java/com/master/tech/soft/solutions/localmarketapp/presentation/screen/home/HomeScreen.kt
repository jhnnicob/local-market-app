package com.master.tech.soft.solutions.localmarketapp.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.HomeTopBar
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.SectionHeader
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.grid.ProductGrid
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.row.CategoryRow
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.row.FarmerRow

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeTopBar (
            name = uiState.value.user?.name ?: "Guest",
            address = uiState.value.user?.address ?: "Unknown Address",
            onSearchClick = { /* TODO */ },
            onFilterClick = { /* TODO */ }
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            CategoryRow(categories = uiState.value.categories)

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader(title = "Recently Listed") {
                // TODO: Navigate to all products
            }
            Spacer(modifier = Modifier.height(16.dp))
            ProductGrid(products = uiState.value.recentProducts)

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader(title = "Best Farmers") {
                // TODO: Navigate to all farmers
            }

            Spacer(modifier = Modifier.height(16.dp))

            FarmerRow(farmers = uiState.value.bestFarmers)
        }
    }
}
