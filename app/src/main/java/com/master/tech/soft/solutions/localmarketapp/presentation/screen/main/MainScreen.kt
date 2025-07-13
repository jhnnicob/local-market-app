package com.master.tech.soft.solutions.localmarketapp.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.master.tech.soft.solutions.localmarketapp.presentation.nav.BottomNavigationBar
import com.master.tech.soft.solutions.localmarketapp.presentation.navigation.AppNavHost

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
