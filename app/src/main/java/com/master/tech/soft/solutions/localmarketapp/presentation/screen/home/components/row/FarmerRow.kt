package com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.master.tech.soft.solutions.localmarketapp.data.model.Farmer
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.FarmerCard

@Composable
fun FarmerRow(farmers: List<Farmer>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(farmers.size) { index ->
            FarmerCard(farmer = farmers[index])
        }
    }
}