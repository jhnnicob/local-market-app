package com.master.tech.soft.solutions.localmarketapp.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.master.tech.soft.solutions.localmarketapp.presentation.navigation.Screen

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem(Screen.Home.route, Icons.Default.Home, "Home")
    object Explore : BottomNavItem(Screen.Explore.route, Icons.Default.Search, "Explore")
    object Add : BottomNavItem(Screen.ProductAdd.route, Icons.Default.AddCircle, "Add")
    object Farmers : BottomNavItem("farmers", Icons.Default.Person, "Farmers")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
}
