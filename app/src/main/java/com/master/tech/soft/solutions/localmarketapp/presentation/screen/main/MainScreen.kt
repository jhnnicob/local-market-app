package com.master.tech.soft.solutions.localmarketapp.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.master.tech.soft.solutions.localmarketapp.presentation.nav.BottomNavigationBar
import com.master.tech.soft.solutions.localmarketapp.presentation.navigation.AppNavHost
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.ProductAddBottomSheet

@Composable
fun MainScreen(navController: NavHostController) {
    val showAddSheet = remember { mutableStateOf(false) }

    val sheetContent = remember {
        mutableStateOf<@Composable () -> Unit>({ })
    }

    LaunchedEffect (Unit) {
        sheetContent.value = {
            ProductAddBottomSheet(
                onDismiss = { showAddSheet.value = false }
            )
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onClicked = {
                    showAddSheet.value = true
                }
            )
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )

        if (showAddSheet.value) {
            sheetContent.value()
        }
    }
}
