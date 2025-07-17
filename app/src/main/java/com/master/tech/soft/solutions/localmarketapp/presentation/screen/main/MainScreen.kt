package com.master.tech.soft.solutions.localmarketapp.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.master.tech.soft.solutions.localmarketapp.presentation.nav.BottomNavigationBar
import com.master.tech.soft.solutions.localmarketapp.presentation.navigation.AppNavHost
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.product.ProductAddBottomSheet
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }

    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onClicked = {
                    scope.launch {
                        sheetState.show()
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )

        if (sheetState.isVisible) {
            ProductAddBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        scope.launch {
                            sheetState.hide()
                        }
                    }
                }
            )
        }
    }
}
