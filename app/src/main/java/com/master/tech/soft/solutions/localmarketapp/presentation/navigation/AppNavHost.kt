package com.master.tech.soft.solutions.localmarketapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.HomeScreen
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.ProductAddScreen
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.ProductDetailScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductAdd: Screen("product_add");
    object ProductDetail : Screen("product_detail/{productId}")
    object Cart : Screen("cart")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.ProductAdd.route) {
            ProductAddScreen(
                onBack = { navController.popBackStack() },
            )
        }
        composable("${Screen.ProductDetail.route}/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
