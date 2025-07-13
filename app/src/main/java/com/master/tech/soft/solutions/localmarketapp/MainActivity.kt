package com.master.tech.soft.solutions.localmarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.main.MainScreen
import com.master.tech.soft.solutions.localmarketapp.ui.theme.LocalMarketAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalMarketAppTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    LocalMarketAppTheme {
//        HomeScreen(
//            navController = rememberNavController(),
//            viewModel = FakeHomeViewModel()
//        )
//    }
//}