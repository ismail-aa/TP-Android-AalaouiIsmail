package com.example.emtyapp.navigator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.emtyapp.ui.auth.AuthEvent
import com.example.emtyapp.ui.auth.AuthState
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.auth.screen.AuthScreen
import com.example.emtyapp.ui.cart.CartViewModel
import com.example.emtyapp.ui.cart.screen.CartScreen
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.component.ProductDetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen
import com.example.emtyapp.ui.profile.ProfileViewModel
import com.example.emtyapp.ui.profile.screens.ProfileScreen

object Routes {
    const val Auth = "auth"
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Profile = "profile"
    const val UserInfo = "userInfo"
    const val OrderTracking = "orderTracking"
    const val StockManagement = "stockManagement"
    const val UserManagement = "userManagement"
    const val Cart = "cart"
}

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    productViewModel: ProductViewModel
) {
    val navController = rememberNavController()
    val authState by authViewModel.state.collectAsState()
    val currentUser = authViewModel.currentUser // Make sure this exists in your AuthViewModel

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Routes.Home) {
                popUpTo(Routes.Auth) { inclusive = true }
            }
            productViewModel.handleIntent(ProductIntent.LoadProducts)
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) Routes.Home else Routes.Auth
    ) {
        composable(Routes.Auth) {
            AuthScreen(
                viewModel = authViewModel,
                onAuthSuccess = { navController.navigate(Routes.Home) },
                navController = navController
            )
        }

        composable(Routes.Home) {
            val cartViewModel: CartViewModel = hiltViewModel()

            HomeScreen(
                viewModel = productViewModel,
                authViewModel = authViewModel,
                cartViewModel = cartViewModel,
                navController = navController,
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
            val state by productViewModel.state.collectAsState()
            val product = state.products.find { it.id == productId }

            if (product != null) {
                ProductDetailsScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
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

        composable(Routes.Profile) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = profileViewModel,
                navController = navController,
                onLogout = { authViewModel.handleEvent(AuthEvent.Logout) }
            )
        }

        composable(Routes.UserInfo) {
            // User info edit screen
        }

        composable(Routes.OrderTracking) {
            // Order tracking detail screen
        }

        composable(Routes.StockManagement) {
            // Stock management screen
        }

        composable(Routes.UserManagement) {
            // User management screen
        }

        composable(Routes.Cart) {
            val cartViewModel: CartViewModel = hiltViewModel()
            CartScreen(
                navController,
                cartViewModel,
                onLogout = { authViewModel.handleEvent(AuthEvent.Logout) }
            )
        }
    }
}