package com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.row

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.master.tech.soft.solutions.localmarketapp.data.model.Category
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.components.CategoryChip
import kotlin.collections.forEach


@Composable
fun CategoryRow(categories: List<Category>) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            CategoryChip(category = category)
        }
    }
}