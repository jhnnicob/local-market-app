package com.master.tech.soft.solutions.localmarketapp.presentation.navigation

import com.master.tech.soft.solutions.localmarketapp.presentation.screen.HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductDetail : Screen("product_detail/{productId}")
    object Cart : Screen("cart")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        // TODO: Add ProductDetail & Cart when available
    }
}
