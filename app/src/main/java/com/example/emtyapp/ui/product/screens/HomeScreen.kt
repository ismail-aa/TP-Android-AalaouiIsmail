package com.example.emtyapp.ui.product.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.component.ProductListScreen

@Composable
fun HomeScreen(viewModel: ProductViewModel, onProductClick: (String) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        // Call loadProducts() method when the composable is first launched.
        viewModel.handleIntent(ProductIntent.LoadProducts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                // Display a Circular loader
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }

            state.error != null -> {
                // Display an error message
                Text(text = "Error: ${state.error}", color = Color.Red)
            }

            else -> {
                // Display products when fetch is success
                ProductListScreen(products = state.products, onProductClick)
            }
        }
    }
}