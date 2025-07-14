package com.master.tech.soft.solutions.localmarketapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.ProductAddScreen
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.ProductDetailScreen
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.home.HomeScreen
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.ProductAddBottomSheet

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductAdd: Screen("product_add");
    object ProductDetail : Screen("product_detail/{productId}")
    object Cart : Screen("cart")
    object Explore : Screen("explore")
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
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
