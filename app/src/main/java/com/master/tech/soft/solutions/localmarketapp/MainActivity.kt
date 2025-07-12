package com.master.tech.soft.solutions.localmarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.HomeScreen
import com.master.tech.soft.solutions.localmarketapp.presentation.screen.HomeViewModel
import com.master.tech.soft.solutions.localmarketapp.ui.theme.LocalMarketAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalMarketAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val homeViewModel: HomeViewModel = hiltViewModel()

                    HomeScreen(
                        navController = navController,
                        viewModel = homeViewModel
                    )
                }
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