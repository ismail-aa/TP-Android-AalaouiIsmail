package com.example.emtyapp.navigator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emtyapp.ui.product.component.ProductDetailsScreen
import com.example.emtyapp.data.repository.ProductRepository
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.screens.HomeScreen

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val repository = remember { ProductRepository() }
    val products = remember { repository.getProducts() } // Get products once when navigation starts

    NavHost(navController = navController, startDestination = Routes.Home) {
        composable(Routes.Home) {
            HomeScreen(
                viewModel = viewModel(factory = ProductViewModel.Factory(repository)),
                onProductClick = { productId ->
                    navController.navigate("${Routes.ProductDetails}/$productId")
                }
            )
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = products.find { it.id == productId }
            if (product != null) {
                ProductDetailsScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                // Error handling if product not found
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Product not found")
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Go back")
                    }
                }
            }
        }
    }
}