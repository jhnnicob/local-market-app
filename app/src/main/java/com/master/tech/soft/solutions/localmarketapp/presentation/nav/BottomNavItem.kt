package com.master.tech.soft.solutions.localmarketapp.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Explore : BottomNavItem("explore", Icons.Default.Search, "Explore")
    object Add : BottomNavItem("add", Icons.Default.AddCircle, "Add")
    object Farmers : BottomNavItem("farmers", Icons.Default.Person, "Farmers")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
}
