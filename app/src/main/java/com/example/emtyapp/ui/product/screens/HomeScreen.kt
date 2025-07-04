package com.example.emtyapp.ui.product.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import com.example.emtyapp.ui.auth.AuthEvent
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.component.ProductListScreen
import androidx.navigation.NavController
import com.example.emtyapp.ui.cart.CartViewModel

@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    navController: NavController,
    onProductClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(ProductIntent.LoadProducts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }
            state.error != null -> {
                Text(text = "Error: ${state.error}", color = Color.Red)
            }
            else -> {
                ProductListScreen(
                    products = state.filteredProducts,
                    onProductClick = onProductClick,
                    onLogout = { authViewModel.handleEvent(AuthEvent.Logout) },
                    viewModel = viewModel,
                    cartViewModel = cartViewModel,
                    navController = navController
                )
            }
        }
    }
}